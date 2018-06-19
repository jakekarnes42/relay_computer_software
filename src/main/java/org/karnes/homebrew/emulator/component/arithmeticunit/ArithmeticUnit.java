package org.karnes.homebrew.emulator.component.arithmeticunit;

import org.karnes.homebrew.bitset.FixedBitSet;
import org.karnes.homebrew.emulator.ConditionCode;
import org.karnes.homebrew.emulator.component.bus.BidirectionalBus;
import org.karnes.homebrew.emulator.component.bus.ReadableBus;
import org.karnes.homebrew.emulator.component.bus.connection.BusValueChangedEvent;
import org.karnes.homebrew.emulator.component.bus.connection.InterruptFromBusConnection;
import org.karnes.homebrew.emulator.component.bus.connection.WriteableBusConnection;


public class ArithmeticUnit {

    private final InterruptFromBusConnection auOperationConnection;
    private final InterruptFromBusConnection tmp1BusConnection;
    private final InterruptFromBusConnection tmp2BusConnection;
    private final WriteableBusConnection outputBusConnection;
    private final WriteableBusConnection ccBusConnection;
    private final int DATA_WIDTH;

    public ArithmeticUnit(ReadableBus auOperationBus, ReadableBus tmp1Bus, ReadableBus tmp2Bus, BidirectionalBus outputBus, BidirectionalBus ccBus) {
        //Get the opcode bus
        if (auOperationBus.getWidth() != 3) {
            throw new IllegalArgumentException("Unexpected width for AU Operation BidirectionalBus. Expected 3. Found: "
                    + auOperationBus.getWidth());
        }

        auOperationConnection = auOperationBus.getInterruptConnection(this::handleAUOperationChange);

        //Get the various data-related buses
        if (tmp1Bus.getWidth() != tmp2Bus.getWidth() || tmp2Bus.getWidth() != outputBus.getWidth()) {
            throw new IllegalArgumentException("All buses for data must have the same width. TMP1 width "
                    + tmp1Bus.getWidth() + "TMP2 width " + tmp2Bus.getWidth() + " Output Width" + outputBus.getWidth());
        }
        DATA_WIDTH = tmp1Bus.getWidth();
        tmp1BusConnection = tmp1Bus.getInterruptConnection(this::handleInputChange);
        tmp2BusConnection = tmp2Bus.getInterruptConnection(this::handleInputChange);
        outputBusConnection = outputBus.getWriteConnection();


        //Get the Condition Code bus
        if (ccBus.getWidth() != 4) {
            throw new IllegalArgumentException("Unexpected width for Condition Code BidirectionalBus. Expected 4. Found: "
                    + ccBus.getWidth());
        }
        ccBusConnection = ccBus.getWriteConnection();

        //Now that we're connected, it's possible that there's already live data, even though this is a no-no
        if (isAUEnabled()) {
            System.err.println("The Arithmetic Unit shouldn't be connected to buses which already have live data");
            updateOutput();
        }
    }

    private void handleAUOperationChange(BusValueChangedEvent e) {
        //The bus contain the AU opcode has changed. Our AU will need to update
        updateOutput();
    }

    public void handleInputChange(BusValueChangedEvent busValueChangedEvent) {
        // If the AU is enabled, and one of the inputs changed, we need to update the output
        if (isAUEnabled()) {
            updateOutput();
        }

    }

    private boolean isAUEnabled() {
        //The third bit of the opcode decides if the AU is enabled.
        return auOperationConnection.readBusValue().get(2);
    }


    private void updateOutput() {
        //Check if the AU should do anything at all.
        if (isAUEnabled()) {
            //AU is enabled. Get the input values
            FixedBitSet opcode = auOperationConnection.readBusValue().getSlice(0, 2);
            FixedBitSet tmp1Value = tmp1BusConnection.readBusValue();
            FixedBitSet tmp2Value = tmp2BusConnection.readBusValue();

            //Hand them off to our simulated circuitry
            performArithmetic(opcode, tmp1Value, tmp2Value);


        } else {
            //The AU is disabled, clear the outputs
            FixedBitSet zero = new FixedBitSet(DATA_WIDTH);
            outputBusConnection.writeValueToBus(zero);
            ccBusConnection.writeValueToBus(zero);
        }
    }


    /*
     * This method attempts to follow the same steps/transformations taken by the hardware.
     * Using the signal names as variables, that's why the naming convention changes here
     */
    private void performArithmetic(FixedBitSet opcode, FixedBitSet A, FixedBitSet B) {
        //Transform the B according to the opcode
        boolean INC_DEC = opcode.get(0);
        if (INC_DEC) {
            //If the INC_DEC line is high, the B is replaced with the number 00..01
            B = new FixedBitSet(DATA_WIDTH).set(0, true);
        }

        //We XOR the B with the SUB signal
        boolean SUB = opcode.get(1);
        for (int i = 0; i < DATA_WIDTH; i++) {
            boolean bOfI = B.get(i);
            B = B.set(i, bOfI ^ SUB);
        }

        //Set up some variables for use later
        FixedBitSet SUM = new FixedBitSet(DATA_WIDTH);
        boolean CIN = SUB; //Initialize CIN with the SUB signal
        boolean COUT = false;
        boolean penultimateCOUT = false;

        //Now we perform the addition on A and B
        for (int i = 0; i < DATA_WIDTH; i++) {
            //Get the bits
            boolean aOfI = A.get(i);
            boolean bOfI = B.get(i);

            //combinatorial logic
            if (!aOfI && !bOfI && !CIN) {
                COUT = false;
                SUM = SUM.set(i, false);
            } else if (!aOfI && !bOfI && CIN) {
                COUT = false;
                SUM = SUM.set(i, true);
            } else if (!aOfI && bOfI && !CIN) {
                COUT = false;
                SUM = SUM.set(i, true);
            } else if (!aOfI && bOfI && CIN) {
                COUT = true;
                SUM = SUM.set(i, false);
            } else if (aOfI && !bOfI && !CIN) {
                COUT = false;
                SUM = SUM.set(i, true);
            } else if (aOfI && !bOfI && CIN) {
                COUT = true;
                SUM = SUM.set(i, false);
            } else if (aOfI && bOfI && !CIN) {
                COUT = true;
                SUM = SUM.set(i, false);
            } else if (aOfI && bOfI && CIN) {
                COUT = true;
                SUM = SUM.set(i, true);
            } else {
                throw new IllegalStateException("Something is wrong with combinatorial logic!");
            }

            //On the second to last bit, we need to save the COUT for later comparison
            if (i == DATA_WIDTH - 2) {
                penultimateCOUT = COUT;
            }

            //Set up for the next iteration
            CIN = COUT;
        }

        //Check for condition codes

        //Zero detection
        boolean ZERO = SUM.equals(new FixedBitSet(DATA_WIDTH));
        //SIGN is the highest bit of the SUM
        boolean SIGN = SUM.get(DATA_WIDTH - 1);
        //OVERFLOW is COUT xor'd with the second to last COUT
        boolean OVERFLOW = COUT ^ penultimateCOUT;
        //CARRY is COUT xor'd with sub
        boolean CARRY = COUT ^ SUB;

        ConditionCode conditionCode = new ConditionCode(CARRY, OVERFLOW, SIGN, ZERO);


        //Set the output
        outputBusConnection.writeValueToBus(SUM);
        ccBusConnection.writeValueToBus(conditionCode.toBitSet());

    }

}


