package com.tmtron.enums.processor;

import com.google.auto.common.MoreElements;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import com.tmtron.enums.EnumMapper;
import com.tmtron.enums.MapAllEnums;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class MapEnumElement {

    public static final String STATEMENT_PUT_CONSTANT_TO_ENUM_MAPPER = "enumMapperBuilder.put($T.$L, value)";
    private final ProcessingEnvironment processingEnvironment;
    private final Element annotatedElement;
    private final TypeElement enumsClassTypeElement;
    private final List<CodeGenEnumConst> enumConsts = new ArrayList<>();
    private final TypeVariableName typeVariableName4Value;

    /**
     * Will process a single Enum class of the {@link com.tmtron.enums.MapAllEnums} annotation.
     *
     * @param processingEnvironment the processing environment
     * @param annotatedElement      the element (e.g. class) which has the {@link MapAllEnums} annotation
     * @param enumsClassTypeElement a single Enum class from the "values" array of the {@link MapAllEnums} annotation
     */
    public MapEnumElement(ProcessingEnvironment processingEnvironment, Element annotatedElement,
                          TypeElement enumsClassTypeElement) {
        this.processingEnvironment = processingEnvironment;
        this.annotatedElement = annotatedElement;
        this.enumsClassTypeElement = enumsClassTypeElement;
        typeVariableName4Value = TypeVariableName.get("V");
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
                enumConsts.add(new CodeGenEnumConst(typeVariableName4Value, element));
            }
        }
        if (enumConsts.size() < 2) throw new RuntimeException("The Enum must have at least 2 constants!");
        writeJavaFile();
    }

    private String getPackageName() {
        return processingEnvironment.getElementUtils().getPackageOf(enumsClassTypeElement).getQualifiedName()
                .toString();
    }

    private AnnotationSpec createGeneratedAnnotation(Class<?> annotationProcessorClass) {
        String dateString = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(AnnotationProcessingUtil.now());
        AnnotationSpec.Builder annotationSpecBuilder = AnnotationSpec.builder(Generated.class)
                .addMember("value", "$S", annotationProcessorClass.getCanonicalName())
                .addMember("date", "$S", dateString);

        String commentsString = "origin=" + MoreElements.asType(annotatedElement).getQualifiedName().toString();
        annotationSpecBuilder.addMember("comments", "$S", commentsString);

        return annotationSpecBuilder.build();
    }

    private void writeJavaFile() {
        /* Example:
         *   enum LauncherAge {
         *     ONLINE, OLD, OFFLINE
         *   }
         */

        // LauncherAge_MapperFull
        ClassName className = ClassName.bestGuess(enumsClassTypeElement.getQualifiedName() + "_MapperFull");
        TypeSpec.Builder mapperFullTypeBuilder = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC,
                Modifier.FINAL);

        mapperFullTypeBuilder.addAnnotation(
                createGeneratedAnnotation(EnumsAnnotationProcessor.class)
        );

        // package.LauncherAge
        ClassName enumClassname = ClassName.bestGuess(enumsClassTypeElement.toString());

        ClassName stagedBuilderClassName = ClassName.get("", "StagedBuilder");
        // StagedBuilder<V>
        ParameterizedTypeName stagedBuilderType = ParameterizedTypeName.get(stagedBuilderClassName,
                typeVariableName4Value);

        ClassName enumMapperClassName = ClassName.get(EnumMapper.class);
        // EnumMapper<LauncherAge, V>
        TypeName lastReturnType = ParameterizedTypeName.get(enumMapperClassName
                , enumClassname.withoutAnnotations(), typeVariableName4Value);
        buildStagedBuilderInterfaces(mapperFullTypeBuilder, typeVariableName4Value, lastReturnType);

        // type for StagedBuilder<V>
        TypeSpec.Builder stagedBuilderTypeBuilder = TypeSpec.classBuilder(stagedBuilderClassName)
                .addTypeVariable(typeVariableName4Value)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC) // TODO: must also be static!
                ;

        //private final EnumMapper.Builder<LauncherAge, V> enumMapperBuilder = EnumMapper.builder(LauncherAge.class);
        ClassName enumMapperBuilderClassName = ClassName.get(EnumMapper.Builder.class);
        TypeName enumMapperBuilderTypeName = ParameterizedTypeName.get(enumMapperBuilderClassName
                , enumClassname.withoutAnnotations(), typeVariableName4Value);
        FieldSpec enumMapperBuilderFieldSpec = FieldSpec.builder(enumMapperBuilderTypeName, "enumMapperBuilder")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .initializer("EnumMapper.builder($T.class)", enumClassname)
                .build();
        stagedBuilderTypeBuilder.addField(enumMapperBuilderFieldSpec);

        // skip the first
        for (int i = 1; i < enumConsts.size(); i++) {
            CodeGenEnumConst enumConst = enumConsts.get(i);
            stagedBuilderTypeBuilder.addSuperinterface(enumConst.interfaceTypeName);

            MethodSpec.Builder interfaceMethodImplBuilder = MethodSpec.methodBuilder(enumConst.setterName)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(typeVariableName4Value, "value")
                    .addStatement(STATEMENT_PUT_CONSTANT_TO_ENUM_MAPPER, enumClassname, enumConst.identifier);

            if (i == enumConsts.size() - 1) {
                // the last interface
                interfaceMethodImplBuilder
                        .addStatement("return enumMapperBuilder.build()")
                        .returns(lastReturnType);
            } else {
                interfaceMethodImplBuilder
                        .addStatement("return this")
                        .returns(enumConsts.get(i + 1).interfaceTypeName);
            }
            stagedBuilderTypeBuilder.addMethod(interfaceMethodImplBuilder.build());
        }

        TypeSpec stagedBuilderTypeSpec = stagedBuilderTypeBuilder.build();
        // add StagedBuilder as inner class
        mapperFullTypeBuilder.addType(stagedBuilderTypeSpec);

        CodeGenEnumConst firstEnumConst = enumConsts.get(0);
        CodeGenEnumConst secondEnumConst = enumConsts.get(1);
        MethodSpec setFirstEnum = MethodSpec.methodBuilder(firstEnumConst.setterName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addTypeVariable(typeVariableName4Value)
                .returns(secondEnumConst.interfaceTypeName)
                .addParameter(typeVariableName4Value, "value")
                .addStatement("$T result = new $T<>()", stagedBuilderType, stagedBuilderClassName)
                .addStatement("result." + STATEMENT_PUT_CONSTANT_TO_ENUM_MAPPER, enumClassname, firstEnumConst
                        .identifier)
                .addStatement("return result")
                .build();
        mapperFullTypeBuilder.addMethod(setFirstEnum);

        try {
            JavaFile.builder(getPackageName(), mapperFullTypeBuilder.build())
                    .skipJavaLangImports(true)
                    .addFileComment("GENERATED") // TODO: use javax Generated annotation!
                    .build()
                    .writeTo(processingEnvironment.getFiler());
        } catch (
                IOException e)

        {
            throw new RuntimeException(e);
        }

    }

    private void buildStagedBuilderInterfaces(TypeSpec.Builder mapperFullTypeBuilder, TypeVariableName
            typeVariableName4Value, TypeName lastReturnType) {
        TypeName returnType = lastReturnType;
        // iterate backwards and skip the first - no interface needed
        for (int i = enumConsts.size() - 1; i > 0; i--) {
            CodeGenEnumConst enumConst = enumConsts.get(i);

            TypeSpec interfaceTypeSpec = TypeSpec.interfaceBuilder(enumConst.interfaceClassName)
                    .addTypeVariable(typeVariableName4Value)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(MethodSpec.methodBuilder(enumConst.setterName)
                            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                            .addParameter(typeVariableName4Value, "value")
                            .returns(returnType)
                            .build()
                    ).build();
            mapperFullTypeBuilder.addType(interfaceTypeSpec);

            // set return type for next lower enum-interface
            returnType = enumConst.interfaceTypeName;
        }
    }

}
