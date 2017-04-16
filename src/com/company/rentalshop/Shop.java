package com.company.rentalshop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Shop {
    private static final int MAX_RENT_COUNT = 3;

    private Map<SportEquipment, Integer> goods = new HashMap<>();
    private Map<SportEquipment, Integer> rentedGoods = new HashMap<>();

    public static Shop getShop(String fileName) {
        Map<SportEquipment, Integer> goods = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (true) {
                String s = reader.readLine();
                if (s == null) {
                    break;
                }
                String[] tokens = s.split(";");
                SportEquipment equipment = new SportEquipment(new Category(tokens[0]), tokens[1], Integer.parseInt(tokens[2]));
                goods.put(equipment, Integer.parseInt(tokens[3]));
            }
        }
        catch (IOException e) {
            ConsoleHelper.writeMessage("Error initializing shop from file. File \"" + fileName + "\" was not found.");
            return null;
        }

        Shop shop = new Shop();
        shop.goods = goods;

        return shop;
    }

    public Map<SportEquipment, Integer> getAvailableGoods() {
        return Collections.unmodifiableMap(goods);
    }

    public RentUnit getRentedGoods() {
        List<SportEquipment> rentedGoodsList = new LinkedList<>();

        for (Map.Entry<SportEquipment, Integer> entry : rentedGoods.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                rentedGoodsList.add(entry.getKey());
            }
        }

        SportEquipment[] rentedGoodsArray = rentedGoodsList.toArray(new SportEquipment[rentedGoodsList.size()]);

        return new RentUnit(rentedGoodsArray);
    }
}
