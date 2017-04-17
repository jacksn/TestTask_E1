package com.company.rentalshop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class ConsoleHelper {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final String AVAILABLE_GOODS_FORMAT_STRING = " %-15s | %-55s | %5s | %9s";

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        try {
            return READER.readLine();
        } catch (IOException e) {
            writeMessage("Error reading string.");
        }
        return "";
    }

    public static int readInt() {
        try {
            String s = readString();
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            writeMessage("Error reading integer.");
        }
        return -1;
    }

    public static void readAnyKey() {
        writeMessage("\nPress \"Enter\" to return to main menu.");
        readString();
    }

    public static void printGoods(SportEquipment[] units) {
        if (units.length == 0) {
            writeMessage("No rented goods available.");
        } else {
            writeMessage("Rented goods:");
            printSeparatorLine();
            for (int i = 0; i < units.length; i++) {
                String s = String.format(" %3d | %-55s ", i + 1, units[i].getName());
                writeMessage(s);
            }
            readAnyKey();
        }
    }

    private static void printSeparatorLine() {
        writeMessage("-----------------------------------------------------------------------------------------------");
    }

    public static void printAvailableGoods(Map<SportEquipment, Integer> availableGoods) {
        if (availableGoods.isEmpty()) {
            writeMessage("No goods available.");
        } else {
            printSeparatorLine();
            writeMessage(String.format(AVAILABLE_GOODS_FORMAT_STRING, "Category", "Name", "Price", "Available"));

            printSeparatorLine();

            for (Map.Entry<SportEquipment, Integer> entry : availableGoods.entrySet()) {
                SportEquipment equipment = entry.getKey();
                writeMessage(String.format(AVAILABLE_GOODS_FORMAT_STRING,
                        equipment.getCategory(), equipment.getName(), equipment.getPrice(), entry.getValue()));
            }
            printSeparatorLine();
            readAnyKey();
        }
    }

    public static void printMainMenu() {
        writeMessage("\nChoose action:" +
                "\n 1. Show available goods" +
                "\n 2. Search good by name" +
                "\n 3. Show rented goods" +
                "\n 4. Exit");
    }
}
