package com.company.rentalshop;

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
                } catch (IllegalArgumentException e) {
                    ConsoleHelper.writeMessage("Invalid action. Please try again.");
                    action = Action.SHOW_MAIN_MENU;
                }

                switch (action) {
                    case SHOW_AVAILABLE:
                        printAvailableGoods();
                        break;
                    case SEARCH_BY_NAME:
                        searchGoodsByName();
                        break;
                    case SHOW_RENTED:
                        printRentedGoods();
                        break;
                }
                showMainMenu();
            } while (action != Action.EXIT);
        }
        ConsoleHelper.writeMessage("\nGood bye!");
    }

    private static void printAvailableGoods() {
        ConsoleHelper.printAvailableGoods(shop.getAvailable());
    }

    private static void searchGoodsByName() {
        ConsoleHelper.writeMessage("Please enter search string:");
        String s = ConsoleHelper.readString();
        if (s.isEmpty()) return;

        ConsoleHelper.printAvailableGoods(shop.findAvailable(s));
    }

    private static void printRentedGoods() {
        ConsoleHelper.printGoods(shop.getRented().getUnits());
    }

    private static void showMainMenu() {
        ConsoleHelper.printMainMenu();
    }
}
