package com.company.rentalshop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleHelper {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final String GOODS_FORMAT_STRING = "%5s | %-15s | %-55s | %5s";

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        try {
            return READER.readLine();
        }
        catch (IOException e) {
            writeMessage("Error reading string.");
        }
        return "";
    }

    public static int readInt() {
        try {
            String s = readString();
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            writeMessage("Error reading integer.");
        }
        return -1;
    }

    public static void printCategories() {
        for (Category category : Category.values()) {
            if (category == Category.ROOT_CATEGORY) {
                continue;
            }
            writeMessage(String.format("%4s. %s", category.ordinal(), Category.getName(category)));
        }
    }

    public static void printGoods(List<SportEquipment> goods) {
        if (goods.isEmpty()) {
            writeMessage("No goods available.");
        } else {
            printHorizontalLine();
            writeMessage(String.format(GOODS_FORMAT_STRING, "No", "Category", "Name", "Price"));
            printHorizontalLine();
            int lineNumber = 1;
            for (SportEquipment good : goods) {
                String s = String.format(GOODS_FORMAT_STRING,
                        lineNumber++, Category.getName(good.getCategory()), good.getTitle(), good.getPrice());
                writeMessage(s);
            }
            printHorizontalLine();
        }
    }

    private static void printHorizontalLine() {
        writeMessage("-----------------------------------------------------------------------------------------------");
    }

    public static void printMainMenu() {
        writeMessage("\nChoose action:" +
                "\n 1. Rent from all available goods" +
                "\n 2. Rent from category" +
                "\n 3. Search goods" +
                "\n 4. Return rented goods" +
                "\n 5. Exit");
    }
}
