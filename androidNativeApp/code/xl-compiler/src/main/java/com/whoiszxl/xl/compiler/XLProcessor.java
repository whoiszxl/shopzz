package com.whoiszxl.xl.compiler;

import com.google.auto.service.AutoService;
import com.whoiszxl.xl.annotations.AppRegisterGenerator;
import com.whoiszxl.xl.annotations.EntryGenerator;
import com.whoiszxl.xl.annotations.PayEntryGenerator;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.Element;

/**
 * @author whoiszxl
 * 模仿黄油刀写的微信代码生成器
 */
@SuppressWarnings("unused")
@AutoService(Processor.class)
public class XLProcessor extends AbstractProcessor {


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> types = new LinkedHashSet<>();
        final Set<Class<? extends Annotation>> supportAnnotations = getSupportedAnnotations();
        for(Class<? extends Annotation> annotation : supportAnnotations) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(EntryGenerator.class);
        annotations.add(PayEntryGenerator.class);
        annotations.add(AppRegisterGenerator.class);
        return annotations;
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        generateEntryCode(env);
        generatePayEntryCode(env);
        generateAppRegisterCode(env);
        return true;
    }


    private void scan(
            RoundEnvironment env,
            Class<? extends Annotation> annotation,
            AnnotationValueVisitor visitor) {
        for (Element typeElement : env.getElementsAnnotatedWith(annotation)) {
            final List<? extends AnnotationMirror> annotationMirrors =
                    typeElement.getAnnotationMirrors();

            for (AnnotationMirror annotationMirror : annotationMirrors) {
                final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                        = annotationMirror.getElementValues();

                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                        : elementValues.entrySet()) {
                    entry.getValue().accept(visitor, null);
                }
            }
        }
    }


    private void generateEntryCode(RoundEnvironment env) {
        final EntryVisitor entryVisitor =
                new EntryVisitor(processingEnv.getFiler());
        scan(env, EntryGenerator.class, entryVisitor);
    }

    private void generatePayEntryCode(RoundEnvironment env) {
        final PayEntryVisitor payEntryVisitor =
                new PayEntryVisitor(processingEnv.getFiler());
        scan(env, PayEntryGenerator.class, payEntryVisitor);
    }

    private void generateAppRegisterCode(RoundEnvironment env) {
        final AppRegisterVisitor appRegisterVisitor =
                new AppRegisterVisitor(processingEnv.getFiler());
        scan(env, AppRegisterGenerator.class, appRegisterVisitor);
    }
}
