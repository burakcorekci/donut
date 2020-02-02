package com.corekcioglu.donut.core.processor.element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public interface ElementProcessor<T extends Element> {
    Map<ElementKind, ElementProcessor> processors = new HashMap<>();

    List<String> determineNames(T element);
    List<String> determineDependencies(T element);
    String getConstructor(T element);

    static <T extends Element> ElementProcessor<T> get(ElementKind elementKind) {
        return processors.computeIfAbsent(elementKind, kind -> {
            switch (kind) {
                case CLASS:
                    return new TypeElementProcessor();
                case METHOD:
                    return new ExecutableElementProcessor();
                default:
                    throw new RuntimeException();
            }
        });
    }
}
