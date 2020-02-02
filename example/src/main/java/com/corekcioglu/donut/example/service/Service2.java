package com.corekcioglu.donut.example.service;

import com.corekcioglu.donut.core.Donut;
import java.time.LocalDate;

@Donut(name = "deneme")
public class Service2 {
    private LocalDate localDate;

    public Service2(LocalDate localDate) {
        this.localDate = localDate;
    }
}
