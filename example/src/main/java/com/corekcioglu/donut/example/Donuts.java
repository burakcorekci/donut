package com.corekcioglu.donut.example;

import com.corekcioglu.donut.core.annotation.Donut;
import java.time.LocalDate;

public class Donuts {

    @Donut
    public static LocalDate localDateDonut() {
        return LocalDate.now();
    }
}
