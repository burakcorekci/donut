package com.corekcioglu.donut.core.generator.model.statement;

import com.corekcioglu.donut.core.generator.model.JStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class FunctionCallStatement extends JStatement {

    @Builder.Default
    private List<JStatement> arguments = new ArrayList<>();

    @Override
    public List<String> generateLines() {
        return Collections.singletonList(getName() + OPENING_BRACKET
                + createCommaSeparatedString(getArguments().stream()
                    .map(JStatement::generateLines)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList()))
                + CLOSING_BRACKET);
    }
}
