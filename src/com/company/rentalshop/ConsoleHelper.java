package com.company.rentalshop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        try {
            String s = READER.readLine();
            return s;
        }
        catch (IOException e) {
            writeMessage("Error reading string.");
        }
        return "";
    }

    public static int readInt() {
        try {
            String s = readString();
            int i = Integer.parseInt(s);
            return i;
        }
        catch (NumberFormatException e) {
            writeMessage("Error reading integer.");
        }
        return -1;
    }

}
