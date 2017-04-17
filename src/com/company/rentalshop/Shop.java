package com.company.rentalshop;

import com.company.rentalshop.exception.RentNotAllowedException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Shop {
    private static final int MAX_RENT_COUNT = 3;

    private Map<SportEquipment, Integer> goods = new HashMap<>();
    private Map<SportEquipment, Integer> rentedGoods = new HashMap<>();


    public Shop(Map<SportEquipment, Integer> goods) {
        this.goods = goods;
    }

    public static Shop getShop(String fileName) {
        Map<SportEquipment, Integer> goods = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (true) {
                String s = reader.readLine();
                if (s == null) {
                    break;
                }
                String[] tokens = s.split(";");
                SportEquipment equipment = new SportEquipment(Category.valueOf(tokens[0]), tokens[1], Integer.parseInt(tokens[2]));
                goods.put(equipment, Integer.parseInt(tokens[3]));
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Error initializing shop from file. File \"" + fileName + "\" was not found.");
            return null;
        }

        return new Shop(goods);
    }

    public void rent(SportEquipment equipment) throws RentNotAllowedException {
        int count = rentedGoods.values().stream().mapToInt(Integer::intValue).sum();
        if (count > MAX_RENT_COUNT) {
            throw new RentNotAllowedException("You not allowed to rent more than " + MAX_RENT_COUNT + " items.");
        }
        rentedGoods.merge(equipment, 1, Integer::sum);
    }

    public Map<SportEquipment, Integer> getAvailable() {
        return Collections.unmodifiableMap(goods);
    }

    public RentUnit getRented() {
        List<SportEquipment> rentedGoodsList = new LinkedList<>();

        for (Map.Entry<SportEquipment, Integer> entry : rentedGoods.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                rentedGoodsList.add(entry.getKey());
            }
        }

        return new RentUnit(rentedGoodsList.toArray(new SportEquipment[rentedGoodsList.size()]));
    }

    public Map<SportEquipment, Integer> findAvailable(String searchString) {
        searchString = searchString.toLowerCase();

        Map<SportEquipment, Integer> result = new HashMap<>();
        for (Map.Entry<SportEquipment, Integer> entry : goods.entrySet()) {
            if (entry.getKey().getName().toLowerCase().contains(searchString)) {
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }
}
