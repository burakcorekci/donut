package com.corekcioglu.donut.example.service;

import com.corekcioglu.donut.core.Donut;

@Donut
public class Service1 extends AbstractService1 implements InterfaceService1 {
    private Service2 service2;

    public Service1(Service2 service2) {
        this.service2 = service2;
    }

    public void sayHello() {
        System.out.println("Hello");
    }

    public void sayBye() {
        System.out.println("Bye");
    }
}
