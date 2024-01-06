package com.hyperpowered.ojvzinn.ptero.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CapacityEnum {

    MEGA(0.9766),
    GIGA(1024.0),
    TERA(1048576.0);

    private final double baseNumber;

    public double transformToMibs(Double value) {
        return baseNumber * value;
    }
}
