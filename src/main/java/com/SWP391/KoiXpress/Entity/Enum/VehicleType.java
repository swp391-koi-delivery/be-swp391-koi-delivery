package com.SWP391.KoiXpress.Entity.Enum;

public enum VehicleType {
    Truck(500),
    Motorcycle(100);

    private final int volume;

    VehicleType(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }
}
