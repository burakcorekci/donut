package com.corekcioglu.donut.core;

public abstract class AbstractDonutManager {
    protected static AbstractDonutManager INSTANCE = null;

    private DonutFactory donutFactory;

    public static AbstractDonutManager get() {
        return INSTANCE;
    }

    protected AbstractDonutManager(DonutFactory donutFactory) {
        this.donutFactory = donutFactory;
    }

    public <T> T getDonut(Class<T> tClass) {
        return donutFactory.getDonut(tClass);
    }
}
