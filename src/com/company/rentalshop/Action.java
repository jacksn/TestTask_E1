package com.company.rentalshop;

public enum Action {
    SHOW_MAIN_MENU,
    RENT_FROM_ALL_AVAILABLE,
    RENT_FROM_CATEGORY,
    SEARCH_BY_NAME,
    RETURN_FROM_RENT,
    EXIT;

    public static Action getByOrdinal(int i) {
        if (i < 0 || i >= Action.values().length) throw new IllegalArgumentException();
        return Action.values()[i];
    }
}
