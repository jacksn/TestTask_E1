package com.company.rentalshop;

import java.util.HashMap;
import java.util.Map;

public enum Action {

    SHOW_MAIN_MENU,
    RENT_FROM_ALL_AVAILABLE,
    RENT_FROM_CATEGORY,
    SEARCH_BY_NAME,
    RETURN_FROM_RENT,
    SHOW_REPORT,
    EXIT;

    private static final Map<Action, String> ACTION_NAMES = new HashMap<Action, String>() {
        {
            put(SHOW_MAIN_MENU, "");
            put(RENT_FROM_ALL_AVAILABLE, "Rent from all available goods");
            put(RENT_FROM_CATEGORY, "Rent from category");
            put(SEARCH_BY_NAME, "Search goods");
            put(RETURN_FROM_RENT, "Return rented goods");
            put(SHOW_REPORT, "Show report");
            put(EXIT, "Exit");
        }
    };

    public static Action getByOrdinal(int i) {
        if (i < 0 || i >= Action.values().length) throw new IllegalArgumentException();
        return Action.values()[i];
    }

    public static String getName(Action action) {
        return ACTION_NAMES.get(action);
    }
}
