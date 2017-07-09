package org.karnes.homebrew.emulator;

import java.util.Scanner;

public class JavaSimulatedIODevice implements IODevice {
    Scanner in;

    public JavaSimulatedIODevice() {
        in = new Scanner(System.in);
    }

    public boolean hasWord() {
        return in.hasNextShort();
    }

    public short getWord() {
        return in.nextShort();
    }

    public void sendWord(short outputShort) {
        System.out.println(outputShort);
    }

    @Override
    protected void finalize() throws Throwable {
        in.close();
    }
}
