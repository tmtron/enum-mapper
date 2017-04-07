package com.tmtron.enums.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class MapEnumElement {

    private final ProcessingEnvironment processingEnvironment;
    private final TypeElement enumsClassTypeElement;
    private final List<Element> enumKindElements = new ArrayList<>();

    public MapEnumElement(ProcessingEnvironment processingEnvironment, TypeElement enumsClassTypeElement) {
        this.processingEnvironment = processingEnvironment;
        this.enumsClassTypeElement = enumsClassTypeElement;
    }

    // e.g. enumsClassTypeElement.getQualifiedName() "com.test.Dummy.BoolEnum.class"
    public void work() {
        String msg = "creating MapEnumElement mapper for: " + enumsClassTypeElement.getQualifiedName();
        processingEnvironment.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
        if (!ElementKind.ENUM.equals(enumsClassTypeElement.getKind())) {
            throw new RuntimeException(enumsClassTypeElement.toString() + " is not an ENUM kind");
        }

        for (Element element : enumsClassTypeElement.getEnclosedElements()) {
            if (ElementKind.ENUM_CONSTANT.equals(element.getKind())) {
                // element.getSimpleName().toString();
                // e.g. RED, BLUE / ON, OFF
                enumKindElements.add(element);
            }
        }
        writeJavaFile();
    }

    private void writeJavaFile() {
        ClassName className = ClassName.bestGuess(enumsClassTypeElement.getQualifiedName() + "_MapperFull");
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        for (Element enumKindElement : enumKindElements) {
            typeBuilder.addField(TypeName.INT, enumKindElement.toString(), Modifier.PUBLIC);
        }

        try {
            JavaFile.builder(getPackageName(), typeBuilder.build())
                    .skipJavaLangImports(true)
                    .addFileComment("GENERATED")
                    .build()
                    .writeTo(processingEnvironment.getFiler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String getPackageName() {
        return processingEnvironment.getElementUtils().getPackageOf(enumsClassTypeElement).getQualifiedName()
                .toString();
    }

}
