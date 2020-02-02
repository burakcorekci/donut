package com.corekcioglu.donut.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Donut {
    String DEFAULT_NAME = "";
    String name() default DEFAULT_NAME;
}
