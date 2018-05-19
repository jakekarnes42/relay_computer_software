package org.karnes.mcp23017;

import mraa.I2c;
import mraa.Result;
import mraa.mraa;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton controlling the I2C bus which allows use to better control synchronization
 */
public class I2CBus {

    private final I2c i2c;
    private final Map<Short, MCP23017> mcp23017Cache = new ConcurrentHashMap<>();

    public static I2CBus getBus() {
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

        //Connect to bus i2c-2 on the Beagle Bone Black
        i2c = new I2c(1);
    }


    public synchronized MCP23017 getMCP23017(int hardwareAddress) {
        return getMCP23017((short) hardwareAddress);
    }

    public synchronized MCP23017 getMCP23017(short hardwareAddress) {
        if (!mcp23017Cache.containsKey(hardwareAddress)) {
            mcp23017Cache.put(hardwareAddress, new MCP23017(this, hardwareAddress));
        }
        return mcp23017Cache.get(hardwareAddress);
    }

    private synchronized Result setAddress(short address) {
        Result addressResult = i2c.address(address);
        if (addressResult != Result.SUCCESS) {
            throw new I2CException("Error getting device at hardware address " + address + ": " + addressResult);
        }
        return addressResult;
    }


    public synchronized void writeByte(short address, short register, byte value) {
        setAddress(address);

        Result writeResult = i2c.writeReg(register, value);
        if (writeResult != Result.SUCCESS) {
            throw new I2CException("Error writing byte at hardware address: " + address +
                    " register: " + register + " value: " + value + " error:" + writeResult);
        }
    }

    public synchronized byte readByte(short address, short register) {
        setAddress(address);

        short readResult = i2c.readReg(register);
        return (byte) readResult;
    }


}
