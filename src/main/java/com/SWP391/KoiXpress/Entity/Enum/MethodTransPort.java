package com.SWP391.KoiXpress.Entity.Enum;

import lombok.Getter;

@Getter
public enum MethodTransPort {
    Fast_delivery(0.42),
    Normal_delivery(0.22);

    private final double price;

    MethodTransPort(double price){
        this.price = price;
    }

}
