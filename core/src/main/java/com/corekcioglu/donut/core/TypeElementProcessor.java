package com.corekcioglu.donut.core;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;

public class TypeElementProcessor implements ElementProcessor<TypeElement> {

    public List<String> determineNames(TypeElement element) {
        List<String> names = new ArrayList<>();
        names.add(element.asType().toString());

        String assignedName = element.getAnnotation(Donut.class).name();
        if (!assignedName.equals(Donut.DEFAULT_NAME)) {
            names.add(assignedName);
        }

        element.getInterfaces().forEach(inter -> names.add(inter.toString()));
        String superClassName = element.getSuperclass().toString();
        if (!superClassName.equals("java.lang.Object")) {
            names.add(superClassName);
        }

        return names;
    }

    public List<String> determineDependencies(TypeElement element) {
        ExecutableType constructor = (ExecutableType) element.getEnclosedElements().stream()
                .filter(child -> child.getSimpleName().contentEquals("<init>"))
                .findFirst().get().asType();
        List<String> arguments = constructor.getParameterTypes().stream()
                .map(TypeMirror::toString).collect(Collectors.toList());
        return arguments;
    }

    public String getConstructor(TypeElement element) {
        return "new " + element.asType().toString();
    }
}
