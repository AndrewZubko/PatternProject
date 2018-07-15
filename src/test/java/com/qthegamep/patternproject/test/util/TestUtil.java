package com.qthegamep.patternproject.test.util;

import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.io.PrintStream;

@UtilityClass
public class TestUtil {

    private final InputStream consoleInputStream = System.in;

    private final PrintStream consolePrintStream = System.out;

    /**
     * This method set input and output to the console. It is used before and after each test where the input
     * or output is substituted.
     */
    public void setInputOutputStreamToConsole() {
        System.setIn(consoleInputStream);
        System.setOut(consolePrintStream);
    }
}
