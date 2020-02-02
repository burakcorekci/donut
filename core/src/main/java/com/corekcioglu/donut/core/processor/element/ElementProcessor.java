package com.corekcioglu.donut.core.processor.element;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public interface ElementProcessor<T extends Element> {
    Map<ElementKind, ElementProcessor> processors = new ConcurrentHashMap<>();

    List<String> determineNames(T element);
    List<String> determineDependencies(T element);
    String getConstructor(T element);

    static ElementProcessor get(ElementKind elementKind) {
        return processors.computeIfAbsent(elementKind, kind -> {
            ElementProcessor processor;
            switch (kind) {
                case CLASS:
                    processor = new TypeElementProcessor();
                    break;
                case METHOD:
                    processor = new ExecutableElementProcessor();
                    break;
                default:
                    throw new RuntimeException();
            }
            return processor;
        });
    }
}
