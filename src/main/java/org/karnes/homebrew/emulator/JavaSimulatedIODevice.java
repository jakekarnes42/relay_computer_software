package org.karnes.homebrew.emulator;

import java.io.*;

/**
 * Helpful simulation of the simple I/O device.
 * <br>
 * This class will likely need to be updated to reflect the final hardware I/O design
 */
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
