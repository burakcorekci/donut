package com.corekcioglu.donut.example;

import com.corekcioglu.donut.example.service.Service1;

public class ExampleApplication {

    public static void main(String[] args) {
        DonutManager donutManager = DonutManager.get();
        Service1 service1 = donutManager.getDonut(Service1.class);
    }
}
