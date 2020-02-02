package com.corekcioglu.donut.core.generator;

import com.corekcioglu.donut.core.DonutFactory;
import com.corekcioglu.donut.core.generator.model.AccessModifier;
import com.corekcioglu.donut.core.generator.model.JClass;
import com.corekcioglu.donut.core.generator.model.JCodeBlock;
import com.corekcioglu.donut.core.generator.model.JMethod;
import com.corekcioglu.donut.core.generator.model.codeblock.StatementCodeBlock;
import com.corekcioglu.donut.core.generator.model.statement.GetDonutStatement;
import com.corekcioglu.donut.core.generator.model.statement.MainNameToDonutPutStatement;
import com.corekcioglu.donut.core.generator.model.statement.NameToMainNamePutStatement;
import com.corekcioglu.donut.core.generator.model.statement.StringLiteral;
import com.corekcioglu.donut.core.generator.model.statement.VariableStatement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DonutFactoryGenerator {

  private DonutFactoryGenerator() {

  }

  public static JClass generate(String packageName, String simpleName,
      Map<String, String> nameToMainName, Map<String, String> mainNameToConstructor,
      Map<String, List<String>> dependencies, List<String> order) {
    return JClass.builder()
        .name(simpleName)
        .packageName(packageName)
        .accessModifier(AccessModifier.PUBLIC)
        .extendedClassName(DonutFactory.class.getCanonicalName())
        .methods(Collections.singletonList(JMethod.builder()
            .name("initializeDonuts")
            .accessModifier(AccessModifier.PROTECTED)
            .returnType("void")
            .block(JCodeBlock.builder()
                .name("Main")
                .blocks(Arrays.asList(
                    JCodeBlock.builder()
                        .name("nameToMainName")
                        .blocks(nameToMainName.keySet().stream()
                            .map(name -> StatementCodeBlock.builder()
                                .statement(NameToMainNamePutStatement.builder()
                                    .arguments(Arrays.asList(
                                        new StringLiteral(name),
                                        new StringLiteral(nameToMainName.get(name))
                                    )).build())
                                .build())
                            .collect(Collectors.toList()))
                        .build(),
                    JCodeBlock.builder()
                        .name("mainNameToDonut")
                        .blocks(order.stream().map(name -> StatementCodeBlock.builder()
                            .statement(MainNameToDonutPutStatement.builder()
                                .name(name)
                                .constructor(mainNameToConstructor.get(name))
                                .getDonutStatements(dependencies.get(name).stream()
                                    .map(dependencyName -> GetDonutStatement.builder()
                                        .arguments(Collections.singletonList(
                                            VariableStatement.builder()
                                                .name(dependencyName + ".class")
                                                .build()))
                                        .build())
                                    .collect(Collectors.toList()))
                                .build())
                            .build())
                            .collect(Collectors.toList()))
                        .build()
                ))
                .build())
            .build()))
        .build();
  }
}
