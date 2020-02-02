package com.corekcioglu.donut.core.generator;

import com.corekcioglu.donut.core.generator.JElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public abstract class JStatement extends JElement {

}
