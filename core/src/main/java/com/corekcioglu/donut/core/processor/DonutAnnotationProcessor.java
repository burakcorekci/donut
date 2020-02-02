package com.corekcioglu.donut.core.processor;

import com.corekcioglu.donut.core.generator.JClass;
import com.corekcioglu.donut.core.processor.element.ElementProcessor;
import com.google.auto.service.AutoService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.corekcioglu.donut.core.annotation.Donut")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class DonutAnnotationProcessor extends AbstractProcessor {

    protected static final Map<String, String> nameToMainName = new HashMap<>();
    protected static final Map<String, String> mainNameToConstructor = new HashMap<>();
    protected static final Map<String, List<String>> dependencies = new HashMap<>();
    protected static final List<String> order = new ArrayList<>();
    protected static String packageName;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv
                    .getElementsAnnotatedWith(annotation);
            for (Element annotatedElement : annotatedElements) {
                ElementKind kind = annotatedElement.getKind();
                ElementProcessor processor = ElementProcessor.get(kind);

                List<String> names = processor.determineNames(annotatedElement);
                String mainName = names.get(0);
                names.forEach(name -> nameToMainName.put(name, mainName));

                List<String> arguments = processor.determineDependencies(annotatedElement);
                dependencies.put(mainName, arguments);

                mainNameToConstructor.put(mainName, processor.getConstructor(annotatedElement));
            }
            determineInitializationOrder();
            determineMainPackage();
            try {
                createDonutFactoryImplementation();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    private void determineInitializationOrder() {
        long count = dependencies.keySet().stream()
                .filter(name -> dependencies.get(name).size() == 0)
                .map(order::add).count();
        while (count != dependencies.size()) {
            count += dependencies.keySet().stream()
                    .filter(name -> !order.contains(name))
                    .filter(name -> dependencies.get(name).stream().map(order::contains)
                            .reduce(true, (sub, elem) -> sub && elem))
                    .map(order::add).count();
        }
    }

    private void determineMainPackage() {
        // TODO: Update determineMainPackage method.
        packageName = "com.corekcioglu.donut.example";
    }

    private void createDonutFactoryImplementation() throws IOException {
        String donutFactoryImplementationSimpleName = "GeneratedDonutFactory";

        JClass generatedDonutFactory = DonutFactoryGenerator
                .generate(packageName, donutFactoryImplementationSimpleName, nameToMainName,
                        mainNameToConstructor, dependencies, order);

        JavaFileObject donutFactoryImplementationFile = processingEnv.getFiler()
                .createSourceFile(packageName + "." + donutFactoryImplementationSimpleName);
        try (PrintWriter out = new PrintWriter(donutFactoryImplementationFile.openWriter())) {
            generatedDonutFactory.generateLines().forEach(out::print);
        }
    }
}
