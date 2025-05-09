package io.kotless.parser.utils.psi

import io.kotless.parser.utils.psi.visitor.KtDefaultVisitor
import org.jetbrains.kotlin.descriptors.CallableDescriptor
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.parents
import org.jetbrains.kotlin.psi.psiUtil.referenceExpression
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.bindingContextUtil.getReferenceTargets
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe

fun KtElement.visitNamedFunctions(filter: (KtNamedFunction) -> Boolean = { true }, body: (KtNamedFunction) -> Unit) {
    accept(object : KtDefaultVisitor() {
        override fun visitNamedFunction(function: KtNamedFunction) {
            if (filter(function)) body(function)

            super.visitNamedFunction(function)
        }
    })
}


/** Tell if this function `static` -- either top-level or in Kotlin Object */
fun KtNamedFunction.isStatic(): Boolean {
    return isTopLevel || (parents.firstOrNull { it is KtClassOrObject } is KtObjectDeclaration)
}

fun KtCallExpression.getReferencedDescriptor(binding: BindingContext): CallableDescriptor? {
    return referenceExpression()?.getReferenceTargets(binding)?.singleOrNull() as CallableDescriptor?
}

fun KtCallExpression.getFqName(binding: BindingContext): String? {
    return getReferencedDescriptor(binding)?.fqNameSafe?.asString()
}
