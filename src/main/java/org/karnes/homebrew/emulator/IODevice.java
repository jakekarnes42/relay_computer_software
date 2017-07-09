package org.karnes.homebrew.emulator;

public interface IODevice {
    boolean hasWord();

    short getWord();

    void sendWord(short outputShort);

}
