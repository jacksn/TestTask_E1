package com.company.rentalshop;

import java.util.HashMap;
import java.util.Map;

public enum Category {
    ROOT_CATEGORY,
    BASKETBALL,
    FOOTBALL,
    SKI_N_SNOWBOARD;

    private static final Map<Category, String> CATEGORY_NAMES = new HashMap<Category, String>() {
        {
            put(ROOT_CATEGORY, "Rental shop");
            put(BASKETBALL, "Basketball");
            put(FOOTBALL, "Football");
            put(SKI_N_SNOWBOARD, "Ski & Snowboard");
        }
    };

    public static String getName(Category category) {
        return CATEGORY_NAMES.get(category);
    }
}
