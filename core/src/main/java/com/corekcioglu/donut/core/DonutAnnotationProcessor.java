package com.corekcioglu.donut.core;

import com.google.auto.service.AutoService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.corekcioglu.donut.core.Donut")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class DonutAnnotationProcessor extends AbstractProcessor {

    protected static final Map<String, String> nameToMainName = new HashMap<>();
    protected static final Map<String, List<String>> dependencies = new HashMap<>();
    protected static final List<String> order = new ArrayList<>();
    protected static String packageName;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<TypeElement> annotatedElements = roundEnv
                    .getElementsAnnotatedWith(annotation).stream()
                    .map(element -> (TypeElement) element)
                    .collect(Collectors.toSet());
            for (TypeElement annotatedClass : annotatedElements) {
                determineNames(annotatedClass);
                determineConstructorArguments(annotatedClass);
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

    private void determineConstructorArguments(TypeElement annotatedClass) {
        ExecutableType constructor = (ExecutableType) annotatedClass.getEnclosedElements().stream()
                .filter(element -> element.getSimpleName().contentEquals("<init>"))
                .findFirst().get().asType();
        List<String> arguments = constructor.getParameterTypes().stream()
                .map(TypeMirror::toString).collect(Collectors.toList());

        String mainName = annotatedClass.asType().toString();
        dependencies.put(mainName, arguments);
    }

    private void determineNames(TypeElement annotatedClass) {
        List<String> names = new ArrayList<>();
        names.add(annotatedClass.asType().toString());
        annotatedClass.getInterfaces().forEach(inter -> names.add(inter.toString()));
        String superClassName = annotatedClass.getSuperclass().toString();
        if (!superClassName.equals("java.lang.Object")) {
            names.add(superClassName);
        }

        String mainName = names.get(0);
        names.forEach(name -> nameToMainName.put(name, mainName));
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
        packageName = "com.corekcioglu.donut.example";
    }

    private void createDonutFactoryImplementation() throws IOException {
        String donutFactoryImplementationSimpleName = "GeneratedDonutFactory";
        String donutFactoryImplementationName =
                packageName + "." + donutFactoryImplementationSimpleName;

        JavaFileObject donutFactoryImplementationFile = processingEnv.getFiler()
                .createSourceFile(donutFactoryImplementationName);
        try (PrintWriter out = new PrintWriter(donutFactoryImplementationFile.openWriter())) {

            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }

            out.println("import com.corekcioglu.donut.core.DonutFactory;");
            out.println();

            out.print("public class ");
            out.print(donutFactoryImplementationSimpleName);
            out.println(" extends DonutFactory {");
            out.println("\t");

            out.println("\tprotected void initializeDonuts() {");

            nameToMainName.keySet().forEach(name -> {
                out.print("\t\tnameToMainName.put(\"");
                out.print(name);
                out.print("\", \"");
                out.print(nameToMainName.get(name));
                out.println("\");");
            });
            out.println("\t\t");
            order.forEach(mainName -> {
                out.print("\t\tmainNameToDonut.put(\"");
                out.print(mainName);
                out.print("\", new ");
                out.print(mainName);
                out.print("(");

                List<String> dependencyList = dependencies.get(mainName);
                for (int i = 0; i < dependencyList.size(); ++i) {
                    String dependencyName = dependencyList.get(i);
                    out.print("getDonut(");
                    out.print(dependencyName);
                    out.print(".class)");
                    if (i != dependencyList.size() - 1) {
                        out.print(", ");
                    }
                }
                out.println("));");
            });

            out.println("\t}");
            out.println("}");
        }
    }
}
