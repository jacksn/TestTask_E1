package com.company.rentalshop;

import java.util.Map;

public class RentalShop {
    private static final String DATA_FILE = "test_data.txt";
    private static Shop shop;

    public static void main(String[] args) {
        ConsoleHelper.writeMessage("Welcome to sport equipment rental shop.");
        shop = Shop.getShop(DATA_FILE);
        if (shop != null) {
            showMainMenu();
            Action action;
            do {
                try {
                    action = Action.getByOrdinal(ConsoleHelper.readInt());
                }
                catch (IllegalArgumentException e) {
                    ConsoleHelper.writeMessage("Invalid action. Please try again.");
                    action = Action.SHOW_MAIN_MENU;
                }

                switch (action) {
                    case SHOW_AVAILABLE:
                        showAvailableGoods();
                        break;
                    case SEARCH_BY_NAME:
                        searchGoodsByName();
                        break;
                    case SHOW_RENTED:
                        showRentedGoods();
                        break;
                }
                showMainMenu();
            } while (action != Action.EXIT);
        }
        ConsoleHelper.writeMessage("\nGood bye!");
    }

    private static void showAvailableGoods() {
        Map<SportEquipment, Integer> availableGoods = shop.getAvailableGoods();
        if (availableGoods.isEmpty()) {
            ConsoleHelper.writeMessage("No goods available.");
        } else {
            printSeparatorLine();
            ConsoleHelper.writeMessage(" Category        | Name                 | Price | Available");
            printSeparatorLine();

            for (Map.Entry<SportEquipment, Integer> entry : availableGoods.entrySet()) {
                SportEquipment equipment = entry.getKey();
                String s = String.format(" %-15s | %-20s | %5d | %9d",
                        equipment.getCategory(), equipment.getName(), equipment.getPrice(), entry.getValue());
                ConsoleHelper.writeMessage(s);
            }
            printSeparatorLine();
            readAnyKey();
        }
    }

    private static void searchGoodsByName() {

    }

    private static void showRentedGoods() {
        SportEquipment[] units = shop.getRentedGoods().getUnits();
        if (units.length == 0) {
            ConsoleHelper.writeMessage("No rented goods available.");
        } else {
            ConsoleHelper.writeMessage("Rented goods:");
            printSeparatorLine();
            for (int i = 0; i < units.length; i++) {
                String s = String.format(" %3d | %-20s ", i + 1, units[i].getName());
                ConsoleHelper.writeMessage(s);
            }
            readAnyKey();
        }
    }

    private static void readAnyKey() {
        ConsoleHelper.writeMessage("\nPress any key to return to main menu.");
        ConsoleHelper.readString();
    }

    private static void printSeparatorLine() {
        ConsoleHelper.writeMessage("------------------------------------------------------------");
    }

    private static void showMainMenu() {
        ConsoleHelper.writeMessage("\nChoose action:" +
                "\n 1. Show available goods" +
                "\n 2. Search good by name" +
                "\n 3. Show rented goods" +
                "\n 4. Exit");
    }
}
