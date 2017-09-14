package org.karnes.homebrew.emulator;

import java.io.*;
import java.nio.charset.Charset;

public class JavaSimulatedIODevice implements IODevice {
    InputStreamReader in;
    OutputStreamWriter out;

    public JavaSimulatedIODevice() {
        this(System.in, System.out);
    }

    public JavaSimulatedIODevice(InputStream in, PrintStream out) {
        this.in = new InputStreamReader(in, Charset.forName("UTF-16"));
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
            return (short) in.read();
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
