package com.corekcioglu.donut.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.ExecutableElement;

public class ExecutableElementProcessor implements ElementProcessor<ExecutableElement> {

    public List<String> determineNames(ExecutableElement element) {
        List<String> names = new ArrayList<>();

        String returnType = element.getReturnType().toString();
        names.add(returnType);

        String assignedName = element.getAnnotation(Donut.class).name();
        if (!assignedName.equals(Donut.DEFAULT_NAME)) {
            names.add(assignedName);
        }

        return names;
    }

    public List<String> determineDependencies(ExecutableElement element) {
        List<String> arguments = element.getParameters().stream()
                .map(param -> param.asType().toString())
                .collect(Collectors.toList());
        return arguments;
    }

    public String getConstructor(ExecutableElement element) {
        return element.getEnclosingElement().toString() + "." + element.getSimpleName();
    }
}
