package com.corekcioglu.donut.core.generator.model.codeblock;

import com.corekcioglu.donut.core.generator.model.JCodeBlock;
import com.corekcioglu.donut.core.generator.model.JStatement;
import com.corekcioglu.donut.core.generator.model.statement.EmptyStatement;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class StatementCodeBlock extends JCodeBlock {

    private JStatement statement;

    @Override
    public List<String> generateLines() {
        if (statement.getClass() == EmptyStatement.class) {
            return Collections.singletonList(NEW_LINE);
        } else {
            return Collections.singletonList(statement.generateLines().get(0) + SEMICOLON);
        }
    }
}
