package org.karnes.homebrew.emulator;

import java.util.Scanner;

public class JavaSimulatedIODevice implements IODevice {
    Scanner in;

    public JavaSimulatedIODevice() {
        in = new Scanner(System.in);
    }

    public boolean hasByte() {
        return in.hasNextByte();
    }

    public byte getByte() {
        return in.nextByte();
    }

    public void sendByte(byte outputByte) {
        System.out.println(outputByte);
    }

    @Override
    protected void finalize() throws Throwable {
        in.close();
    }
}
