package org.karnes.homebrew.relay.common.hardware;

public enum MCP23017Pin {

    GPIOA_0(MCP23017Port.A, (byte) 0),
    GPIOA_1(MCP23017Port.A, (byte) 1),
    GPIOA_2(MCP23017Port.A, (byte) 2),
    GPIOA_3(MCP23017Port.A, (byte) 3),
    GPIOA_4(MCP23017Port.A, (byte) 4),
    GPIOA_5(MCP23017Port.A, (byte) 5),
    GPIOA_6(MCP23017Port.A, (byte) 6),
    GPIOA_7(MCP23017Port.A, (byte) 7),

    GPIOB_0(MCP23017Port.B, (byte) 0),
    GPIOB_1(MCP23017Port.B, (byte) 1),
    GPIOB_2(MCP23017Port.B, (byte) 2),
    GPIOB_3(MCP23017Port.B, (byte) 3),
    GPIOB_4(MCP23017Port.B, (byte) 4),
    GPIOB_5(MCP23017Port.B, (byte) 5),
    GPIOB_6(MCP23017Port.B, (byte) 6),
    GPIOB_7(MCP23017Port.B, (byte) 7);

    private final MCP23017Port port;
    private final byte position;

    MCP23017Pin(MCP23017Port port, byte position) {
        this.port = port;
        this.position = position;
    }

    public byte getPosition() {
        return position;
    }

    public MCP23017Port getPort() {
        return port;
    }
}
