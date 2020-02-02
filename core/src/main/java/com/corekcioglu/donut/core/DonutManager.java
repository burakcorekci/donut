package com.corekcioglu.donut.core;

import java.util.Objects;

public class DonutManager {
    private static DonutManager INSTANCE = null;

    private DonutFactory donutFactory;

    public static DonutManager create(DonutFactory donutFactory) {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new DonutManager(donutFactory);
        }
        return INSTANCE;
    }

    public static DonutManager get() {
        return INSTANCE;
    }

    private DonutManager(DonutFactory donutFactory) {
        this.donutFactory = donutFactory;
    }

    public <T> T getDonut(Class<T> tClass) {
        return tClass.cast(donutFactory.getDonut(tClass));
    }
}
