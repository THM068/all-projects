package io.kotless.parser.processor

import io.kotless.parser.utils.psi.annotation.getAnnotations
import io.kotless.parser.utils.psi.annotation.isAnnotatedWith
import io.kotless.parser.utils.psi.isStatic
import io.kotless.parser.utils.psi.visitClassOrObject
import io.kotless.parser.utils.psi.visitNamedFunctions
import io.kotless.parser.utils.psi.visitVariables
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.resolve.BindingContext
import kotlin.reflect.KClass

/**
 * Abstract implementation of [Processor] that provides utils for
 * analysis of elements annotated with some specific annotation
 */
abstract class AnnotationProcessor<Output : Any> : Processor<Output>() {
    abstract val annotations: Set<KClass<out Annotation>>

    fun processClassesOrObjects(files: Set<KtFile>, binding: BindingContext, body: (KtClassOrObject, KtAnnotationEntry, KClass<*>) -> Unit) {
        for (file in files) {
            file.visitClassOrObject(filter = { classOrObject -> classOrObject.isAnnotatedWith(binding, annotations) }) { classOrObject ->
                for (annotationKClass in annotations) {
                    classOrObject.getAnnotations(binding, annotationKClass).forEach { annotation ->
                        body(classOrObject, annotation, annotationKClass)
                    }
                }
            }
        }
    }

    fun processStaticFunctions(files: Set<KtFile>, binding: BindingContext, body: (KtNamedFunction, KtAnnotationEntry, KClass<*>) -> Unit) {
        for (file in files) {
            file.visitNamedFunctions(filter = { function -> function.isAnnotatedWith(binding, annotations) && function.isStatic() }) { func: KtNamedFunction ->
                for (annotationKClass in annotations) {
                    func.getAnnotations(binding, annotationKClass).forEach { annotation ->
                        body(func, annotation, annotationKClass)
                    }
                }
            }
        }
    }

    fun processStaticVariables(files: Set<KtFile>, binding: BindingContext, body: (KtProperty, KtAnnotationEntry, KClass<*>) -> Unit) {
        for (file in files) {
            file.visitVariables(filter = { property -> property.isAnnotatedWith(binding, annotations) }) { property ->
                for (annotationKClass in annotations) {
                    property.getAnnotations(binding, annotationKClass).forEach { annotation ->
                        body(property, annotation, annotationKClass)
                    }
                }
            }
        }
    }
}
