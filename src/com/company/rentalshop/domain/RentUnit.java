package com.company.rentalshop.domain;

public class RentUnit {

    private final SportEquipment[] units;

    public RentUnit(SportEquipment[] units) {
        this.units = units;
    }

    public SportEquipment[] getUnits() {
        return units;
    }

}
