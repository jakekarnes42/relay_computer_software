package org.karnes.homebrew.relay.computer.emulator;

public interface IODevice {
    boolean hasWord();

    short getWord();

    void sendWord(short outputShort);

    boolean canSendWord();
}
