package com.corekcioglu.donut.core.generator;

import com.corekcioglu.donut.core.AbstractDonutManager;
import com.corekcioglu.donut.core.generator.model.AccessModifier;
import com.corekcioglu.donut.core.generator.model.JClass;
import com.corekcioglu.donut.core.generator.model.JCodeBlock;
import com.corekcioglu.donut.core.generator.model.JMethod;
import com.corekcioglu.donut.core.generator.model.codeblock.IfElseCodeBlock;
import com.corekcioglu.donut.core.generator.model.codeblock.StatementCodeBlock;
import com.corekcioglu.donut.core.generator.model.statement.CastStatement;
import com.corekcioglu.donut.core.generator.model.statement.FunctionCallStatement;
import com.corekcioglu.donut.core.generator.model.statement.ReturnStatement;
import com.corekcioglu.donut.core.generator.model.statement.TwoArgOperatorStatement;
import com.corekcioglu.donut.core.generator.model.statement.VariableStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DonutManagerGenerator {

  private DonutManagerGenerator() {

  }

  public static JClass generate(String packageName) {
    VariableStatement instanceVariableStatement = VariableStatement.builder()
        .name("INSTANCE")
        .build();

    String simpleName = "DonutManager";

    return JClass.builder()
        .packageName(packageName)
        .name(simpleName)
        .accessModifier(AccessModifier.PUBLIC)
        .extendedClassName(AbstractDonutManager.class.getCanonicalName())
        .importedClassNames(new ArrayList<>(Arrays.asList("java.util.Objects")))
        .methods(Arrays.asList(
            JMethod.builder()
                .accessModifier(AccessModifier.PROTECTED)
                .name(simpleName)
                .isConstructor(true)
                .block(StatementCodeBlock.builder().statement(
                    FunctionCallStatement.builder()
                        .name("super")
                        .arguments(Collections.singletonList(
                            FunctionCallStatement.builder()
                                .name("new GeneratedDonutFactory")
                                .build()
                        ))
                        .build()
                ).build())
                .build(),
            JMethod.builder()
                .accessModifier(AccessModifier.PUBLIC)
                .isStatic(true)
                .returnType(simpleName)
                .name("get")
                .block(JCodeBlock.builder()
                    .blocks(Arrays.asList(
                        IfElseCodeBlock.builder()
                            .ifStatement(FunctionCallStatement.builder()
                                .name("Objects.isNull")
                                .arguments(Collections
                                    .singletonList(
                                        instanceVariableStatement))
                                .build())
                            .ifBlock(StatementCodeBlock.builder()
                                .statement(TwoArgOperatorStatement
                                    .builder()
                                    .operator("=")
                                    .leftStatement(
                                        instanceVariableStatement)
                                    .rightStatement(
                                        FunctionCallStatement
                                            .builder()
                                            .name("new DonutManager")
                                            .build())
                                    .build())
                                .build())
                            .build(),
                        StatementCodeBlock.builder()
                            .statement(ReturnStatement.builder()
                                .statement(CastStatement.builder()
                                    .type(simpleName)
                                    .toBeCasted(instanceVariableStatement)
                                    .build())
                                .build())
                            .build()
                    ))
                    .build())
                .build()
        ))
        .build();
  }
}
