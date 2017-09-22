package org.karnes.homebrew.emulator;

import java.io.*;

public class JavaSimulatedIODevice implements IODevice {
    Reader in;
    OutputStreamWriter out;

    public JavaSimulatedIODevice() {
        this(System.in, System.out);
    }

    public JavaSimulatedIODevice(InputStream in, PrintStream out) {
        this.in = new InputStreamReader(in);
        this.out = new OutputStreamWriter(out);
    }

    public boolean hasWord() {
        try {
            return in.ready();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public short getWord() {
        try {
            int read = in.read();
            char readChar = (char) read;
            return (short) readChar;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void sendWord(short outputShort) {
        try {
            out.write(outputShort);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
