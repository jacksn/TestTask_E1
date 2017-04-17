package com.company.rentalshop;

import com.company.rentalshop.exception.NotFoundException;
import com.company.rentalshop.exception.OutOfStockException;
import com.company.rentalshop.exception.RentNotAllowedException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                SportEquipment equipment =
                        new SportEquipment(Category.valueOf(tokens[0]), tokens[1], Integer.parseInt(tokens[2]));
                goods.put(equipment, Integer.parseInt(tokens[3]));
            }
        }
        catch (IOException e) {
            ConsoleHelper.writeMessage("Error initializing shop from file. File \"" + fileName + "\" was not found.");
            return null;
        }

        return new Shop(goods);
    }

    public void rent(SportEquipment equipment) throws RentNotAllowedException, OutOfStockException {
        int count = rentedGoods.values().stream().mapToInt(Integer::intValue).sum();
        if (count >= MAX_RENT_COUNT) {
            throw new RentNotAllowedException("You are not allowed to rent more than " + MAX_RENT_COUNT + " items.");
        }
        int availableCount = goods.get(equipment);
        if (availableCount > 0) {
            goods.merge(equipment, -1, Integer::sum);
            rentedGoods.merge(equipment, 1, Integer::sum);
        } else {
            throw new OutOfStockException("\"" + equipment.getTitle() + "\" is out of stock.");
        }
    }

    public void returnToShop(SportEquipment equipment) throws NotFoundException {
        int rentedCount = rentedGoods.get(equipment);
        if (rentedCount > 0) {
            goods.merge(equipment, 1, Integer::sum);
            if (rentedCount == 1) {
                rentedGoods.remove(equipment);
            } else {
                rentedGoods.merge(equipment, -1, Integer::sum);
            }
        } else {
            throw new NotFoundException("\"" + equipment.getTitle() + "\" is not rented.");
        }
    }

    public List<SportEquipment> getAvailable(Category category) {
        List<SportEquipment> equipmentList = goods.entrySet().stream()
                .filter(entry -> entry.getValue() > 0
                        && (category == Category.ROOT_CATEGORY || entry.getKey().getCategory() == category))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        Collections.sort(equipmentList);
        return equipmentList;
    }

    public List<SportEquipment> searchAvailable(String searchString) {
        searchString = searchString.toLowerCase();

        List<SportEquipment> equipmentList = new LinkedList<>();

        for (SportEquipment equipment : goods.keySet()) {
            if (equipment.getTitle().toLowerCase().contains(searchString)) {
                equipmentList.add(equipment);
            }
        }

        Collections.sort(equipmentList);
        return equipmentList;
    }

    public RentUnit getRented() {
        List<SportEquipment> equipmentList = new LinkedList<>();

        for (Map.Entry<SportEquipment, Integer> entry : rentedGoods.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                equipmentList.add(entry.getKey());
            }
        }

        Collections.sort(equipmentList);
        SportEquipment[] units = equipmentList.toArray(new SportEquipment[equipmentList.size()]);
        return new RentUnit(units);
    }
}
