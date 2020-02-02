package com.corekcioglu.donut.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class DonutFactory {
    protected Map<String, String> nameToMainName = new ConcurrentHashMap<>();
    protected Map<String, Object> mainNameToDonut = new ConcurrentHashMap<>();

    public DonutFactory() {
        initializeDonuts();
    }

    protected abstract void initializeDonuts();

    public <T> T getDonut(Class<T> tClass) {
        return tClass.cast(getDonut(tClass.getName()));
    }

    public Object getDonut(String name) {
        String mainName = nameToMainName.get(name);
        return mainNameToDonut.get(mainName);
    }
}
