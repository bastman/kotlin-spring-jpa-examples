package com.example.demo.api.common.validation

import com.example.demo.api.common.BadRequestException
import org.springframework.validation.DataBinder
import org.springframework.validation.Validator

fun <T : Any> T?.notNull(field: String): T {
    return this ?: throw BadRequestException("field=$field must be not null")
}

fun String.notBlank(requestPropertyName: String): String {
    if (this.isBlank()) {
        throw BadRequestException("$requestPropertyName must not be blank!")
    }

    return this
}

fun <T : Any> T.validateBean(validator: Validator, beanName: String?): T {
    val value = this
    val binder = if (beanName == null) {
        DataBinder(value)
    } else {
        DataBinder(value, beanName)
    }
    binder.validator = validator
    binder.validate()
    val result = binder.bindingResult
    if (!result.hasErrors()) {

        return value
    }

    val errors = result.fieldErrors.map {
        mapOf<String, Any?>(
                "field" to "${it.objectName}.${it.field}",
                "message" to it.defaultMessage
        )
    }

    val classname: String = this::class.qualifiedName ?: this::class.toString()

    val msg = "Failed to validate bean=$classname objectName: ${result.objectName} nestedPath: ${result.nestedPath} allErrors:${errors.joinToString("\n")}"

    throw BadRequestException(msg)
}
