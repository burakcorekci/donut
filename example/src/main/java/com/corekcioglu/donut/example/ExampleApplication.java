package com.corekcioglu.donut.example;

import com.corekcioglu.donut.core.DonutManager;

public class ExampleApplication {

    public static void main(String[] args) {
        DonutManager donutManager = DonutManager.create(new GeneratedDonutFactory());
    }
}
