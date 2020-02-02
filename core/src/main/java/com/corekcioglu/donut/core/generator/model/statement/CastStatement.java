package com.corekcioglu.donut.core.generator.model.statement;

import com.corekcioglu.donut.core.generator.model.JStatement;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class CastStatement extends JStatement {

  private String type;
  private JStatement toBeCasted;

  @Override
  public List<String> generateLines() {
    return Collections.singletonList(
        OPENING_BRACKET + getType() + CLOSING_BRACKET + SPACE + toBeCasted.generate());
  }
}
