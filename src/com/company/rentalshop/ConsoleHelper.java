package com.company.rentalshop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class ConsoleHelper {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final String GOODS_FORMAT_STRING = "%5s | %-15s | %-55s | %5s";
    private static final String GOODS_WITH_COUNT_FORMAT_STRING = "%4s | %-15s | %-50s | %5s | %5s";

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
            printTableHeader(GOODS_FORMAT_STRING, "No", "Category", "Name", "Price");
            int lineNumber = 1;
            for (SportEquipment good : goods) {
                String s = String.format(GOODS_FORMAT_STRING,
                        lineNumber++, Category.getName(good.getCategory()), good.getTitle(), good.getPrice());
                writeMessage(s);
            }
            printHorizontalLine();
        }
    }

    public static void printGoodsWithCount(Map<SportEquipment, Integer> goods) {
        if (goods.isEmpty()) {
            writeMessage("No goods available.");
        } else {
            printTableHeader(GOODS_WITH_COUNT_FORMAT_STRING, "No", "Category", "Name", "Price", "Count");
            int lineNumber = 1;
            for (Map.Entry<SportEquipment, Integer> entry : goods.entrySet()) {
                SportEquipment equipment = entry.getKey();
                String s = String.format(GOODS_WITH_COUNT_FORMAT_STRING,
                        lineNumber++,
                        Category.getName(equipment.getCategory()),
                        equipment.getTitle(),
                        equipment.getPrice(),
                        entry.getValue());
                writeMessage(s);
            }
            printHorizontalLine();
        }
    }
    private static void printTableHeader(final String formatString, String... columnNames) {
        printHorizontalLine();
        writeMessage(String.format(formatString, columnNames));
        printHorizontalLine();
    }


    private static void printHorizontalLine() {
        writeMessage("--------------------------------------------------------------------------------------------");
    }

    public static void printMainMenu() {
        writeMessage("\nChoose action:");
        Action[] actions = Action.values();
        for (int i = 1; i < actions.length; i++) {
            writeMessage(String.format("%4s. %s", i, Action.getName(actions[i])));
        }
    }
}
