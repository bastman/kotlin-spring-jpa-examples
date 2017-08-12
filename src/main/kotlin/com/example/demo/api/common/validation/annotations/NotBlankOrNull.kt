package com.example.demo.api.common.validation.annotations

import org.hibernate.validator.constraints.CompositionType.OR
import org.hibernate.validator.constraints.ConstraintComposition
import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.constraints.Null
import kotlin.reflect.KClass
import org.hibernate.validator.constraints.NotBlank as HibernateNotBlank

@ConstraintComposition(OR)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = arrayOf())
@MustBeDocumented

@Null
@NotBlankComposition
annotation class NotBlankOrNull(
        // see: https://github.com/lukaszguz/optional-field-validation/blob/master/src/main/java/pl/guz/domain/validation/OptionalSpecialField.java
        val message: String = "{}",
        val groups: Array<KClass<*>> = arrayOf(),
        val payload: Array<KClass<out Payload>> = arrayOf()
)

@ConstraintComposition
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = arrayOf())
@MustBeDocumented
@HibernateNotBlank
annotation class NotBlankComposition(
        // see: // see: https://github.com/lukaszguz/optional-field-validation/blob/master/src/main/java/pl/guz/domain/validation/OptionalSpecialField.java

        val message: String = "{}",
        val groups: Array<KClass<*>> = arrayOf(),
        val payload: Array<KClass<out Payload>> = arrayOf()
)



