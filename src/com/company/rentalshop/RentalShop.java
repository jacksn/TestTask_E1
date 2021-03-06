package com.company.rentalshop;

import com.company.rentalshop.domain.Category;
import com.company.rentalshop.domain.RentUnit;
import com.company.rentalshop.domain.SportEquipment;
import com.company.rentalshop.util.ConsoleHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RentalShop {
    private static final String DATA_FILE = "test_data.txt";

    private static Shop shop;

    public static void main(String[] args) {
        shop = Shop.getShop(DATA_FILE);
        ConsoleHelper.writeMessage("Welcome to sport equipment rental shop.");
        if (shop != null) {
            Action action = Action.SHOW_MAIN_MENU;
            do {

                switch (action) {
                    case RENT_FROM_ALL_AVAILABLE:
                        rentFromAllAvailableGoods();
                        action = Action.SHOW_MAIN_MENU;
                        break;
                    case RENT_FROM_CATEGORY:
                        rentFromCategory();
                        action = Action.SHOW_MAIN_MENU;
                        break;
                    case SEARCH_BY_NAME:
                        searchGoodsByName();
                        action = Action.SHOW_MAIN_MENU;
                        break;
                    case RETURN_FROM_RENT:
                        returnFromRent();
                        action = Action.SHOW_MAIN_MENU;
                        break;
                    case SHOW_REPORT:
                        showReport();
                        action = Action.SHOW_MAIN_MENU;
                        break;
                    case SHOW_MAIN_MENU:
                        showMainMenu();
                        try {
                            action = Action.getByOrdinal(ConsoleHelper.readInt());
                        }
                        catch (IllegalArgumentException e) {
                            ConsoleHelper.writeMessage("Invalid action. Please try again.");
                            action = Action.SHOW_MAIN_MENU;
                        }
                        break;
                }
            } while (action != Action.EXIT);
        }
        ConsoleHelper.writeMessage("Good bye!");
    }

    private static void showReport() {
        ConsoleHelper.writeMessage("Full report:");
        ConsoleHelper.writeMessage("\nAvailable goods:");
        Map<SportEquipment, Integer> available = new TreeMap<>(shop.getGoods());
        ConsoleHelper.printGoodsWithCount(available);
        ConsoleHelper.writeMessage("\nRented goods:");
        Map<SportEquipment, Integer> rented = convertRentUnitToMap(shop.getRented());
        ConsoleHelper.printGoodsWithCount(rented);
        ConsoleHelper.writeMessage("\nPress Enter to return to main menu.");
        ConsoleHelper.readString();
    }

    private static Map<SportEquipment, Integer> convertRentUnitToMap(RentUnit rentUnit) {
        Map<SportEquipment, Integer> result = new TreeMap<>();
        for (SportEquipment equipment : rentUnit.getUnits()) {
            result.merge(equipment, 1, Integer::sum);
        }
        return result;
    }

    private static void rentFromAllAvailableGoods() {
        rentFromAvailable(shop.getAvailable(Category.ROOT_CATEGORY));
    }

    private static void rentFromCategory() {
        ConsoleHelper.printCategories();
        ConsoleHelper.writeMessage("Please enter category number or \"0\" to return to main menu");
        int selectedCategory = ConsoleHelper.readInt();
        rentFromAvailable(shop.getAvailable(Category.values()[selectedCategory]));
    }

    private static void searchGoodsByName() {
        ConsoleHelper.writeMessage("Please enter search string:");
        String s = ConsoleHelper.readString();
        if (s.isEmpty()) {
            return;
        }
        rentFromAvailable(shop.searchAvailable(s));
    }

    private static void rentFromAvailable(List<SportEquipment> availableGoods) {
        if (availableGoods.isEmpty()) {
            ConsoleHelper.writeMessage("No goods available.");
            return;
        }
        ConsoleHelper.writeMessage("Available goods:");
        ConsoleHelper.printGoods(availableGoods);
        ConsoleHelper.writeMessage("Please enter good number to rent or \"0\" to return to main menu");
        int selectedGood = ConsoleHelper.readInt();
        if (selectedGood > 0 && selectedGood <= availableGoods.size()) {
            try {
                SportEquipment equipment = availableGoods.get(selectedGood - 1);
                shop.rent(equipment);
                ConsoleHelper.writeMessage("You successfully rented \"" + equipment.getTitle() + "\".");
            }
            catch (Exception e) {
                ConsoleHelper.writeMessage("Failed to rent item: " + e.getMessage());
            }
        }
    }

    private static void returnFromRent() {
        int selectedGood;
        do {
            List<SportEquipment> rentedGoods = Arrays.asList(shop.getRented().getUnits());
            if (rentedGoods.isEmpty()) {
                ConsoleHelper.writeMessage("No rented goods.");
                return;
            }
            ConsoleHelper.writeMessage("Rented goods:");
            ConsoleHelper.printGoods(rentedGoods);
            ConsoleHelper.writeMessage("Please enter good number to return to the shop or \"0\" to return to main menu");
            selectedGood = ConsoleHelper.readInt();
            if (selectedGood > 0 && selectedGood <= rentedGoods.size()) {
                try {
                    SportEquipment equipment = rentedGoods.get(selectedGood - 1);
                    shop.returnToShop(equipment);
                    ConsoleHelper.writeMessage("You successfully returned \"" + equipment.getTitle() + "\" to the shop.");
                    return;
                }
                catch (Exception e) {
                    ConsoleHelper.writeMessage("Failed to rent item: " + e.getMessage());
                    return;
                }
            }
        } while (selectedGood != 0);
    }

    private static void showMainMenu() {
        ConsoleHelper.printMainMenu();
    }
}
