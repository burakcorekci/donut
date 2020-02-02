package com.corekcioglu.donut.core.processor;

import com.corekcioglu.donut.core.DonutFactory;
import com.corekcioglu.donut.core.generator.AccessModifier;
import com.corekcioglu.donut.core.generator.EmptyStatement;
import com.corekcioglu.donut.core.generator.GetDonutStatement;
import com.corekcioglu.donut.core.generator.JClass;
import com.corekcioglu.donut.core.generator.JMethod;
import com.corekcioglu.donut.core.generator.MainNameToDonutPutStatement;
import com.corekcioglu.donut.core.generator.NameToMainNamePutStatement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                        .statements(Stream.of(
                                nameToMainName.keySet().stream()
                                        .map(name -> NameToMainNamePutStatement
                                                .builder()
                                                .name(name)
                                                .mainName(nameToMainName.get(name))
                                                .build()).collect(Collectors.toList()),
                                Collections.singletonList(EmptyStatement.builder().build()),
                                order.stream().map(name -> MainNameToDonutPutStatement.builder()
                                        .name(name)
                                        .constructor(mainNameToConstructor.get(name))
                                        .getDonutStatements(dependencies.get(name).stream()
                                                .map(dependencyName -> GetDonutStatement.builder()
                                                        .name(dependencyName)
                                                        .build())
                                                .collect(Collectors.toList()))
                                        .build()).collect(Collectors.toList())
                        ).flatMap(Collection::stream).collect(Collectors.toList()))
                        .build()))
                .build();
    }
}
