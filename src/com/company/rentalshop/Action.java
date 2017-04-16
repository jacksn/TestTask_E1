package com.company.rentalshop;

public enum Action {
    SHOW_MAIN_MENU,
    SHOW_AVAILABLE,
    SEARCH_BY_NAME,
    SHOW_RENTED,
    EXIT;

    public static Action getByOrdinal(int i) {
        if (i < 0 || i >= Action.values().length) throw new IllegalArgumentException();
        return Action.values()[i];
    }
}
