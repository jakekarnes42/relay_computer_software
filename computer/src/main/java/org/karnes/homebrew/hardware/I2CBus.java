package org.karnes.homebrew.hardware;

import mraa.I2c;
import mraa.Result;
import mraa.mraa;
import org.karnes.homebrew.bitset.FixedBitSet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton controlling the I2C bus which allows use to better control synchronization
 */
public class I2CBus {

    private final I2c i2c;
    private final Map<MCP23017Address, MCP23017> mcp23017Cache = new ConcurrentHashMap<>();

    public static I2CBus getI2CBus() {
        return SingletonHolder.instance;
    }

    /**
     * Just a holder for safety. Nothing special
     */
    private static class SingletonHolder {
        public static final I2CBus instance = new I2CBus();
    }


    private I2CBus() {
        //Only need to load the native lib once
        try {
            System.loadLibrary("mraajava");
        } catch (UnsatisfiedLinkError e) {

            throw new I2CException(
                    "Native code library (mraajava) failed to load:", e);
        }

        //Initialize the native lib
        mraa.init();

        //Connect to i2c bus on the Raspberry Pi
        i2c = new I2c(1, true);
    }


    public synchronized MCP23017 getMCP23017(MCP23017Address hardwareAddress) {
        if (!mcp23017Cache.containsKey(hardwareAddress)) {
            mcp23017Cache.put(hardwareAddress, new MCP23017(this, hardwareAddress));
        }
        return mcp23017Cache.get(hardwareAddress);
    }

    private synchronized Result setAddress(MCP23017Address address) {
        Result addressResult = i2c.address(address.getHardwareValue());
        if (addressResult != Result.SUCCESS) {
            throw new I2CException("Error getting device at hardware address " + address + ": " + addressResult);
        }
        return addressResult;
    }


    public synchronized void writeByte(MCP23017Address address, short register, FixedBitSet value) {
        setAddress(address);

        Result writeResult = i2c.writeReg(register, value.toByte());
        if (writeResult != Result.SUCCESS) {
            throw new I2CException("Error writing byte at hardware address: " + address +
                    " register: " + register + " value: " + value + " error:" + writeResult);
        }
    }

    public synchronized FixedBitSet readByte(MCP23017Address address, short register) {
        setAddress(address);

        short readResult = i2c.readReg(register);
        return FixedBitSet.fromByte((byte) readResult);
    }


}
