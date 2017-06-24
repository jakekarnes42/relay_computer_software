package org.karnes.homebrew.emulator;

public interface IODevice {
    public boolean hasByte();

    public byte getByte();

    public void sendByte(byte outputByte);
}
