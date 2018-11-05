package org.karnes.homebrew.relay.common.hardware;
public enum MCP23017Address {
    ADDR0(0x20),
    ADDR1(0x21),
    ADDR2(0x22),
    ADDR3(0x23),
    ADDR4(0x24),
    ADDR5(0x25),
    ADDR6(0x26),
    ADDR7(0x27);

    //Because the underlying library wants shorts
    private final short hardwareValue;

    MCP23017Address(int hardwareValue) {
        this.hardwareValue = (short) hardwareValue;
    }

    public short getHardwareValue() {
        return hardwareValue;
    }
}
