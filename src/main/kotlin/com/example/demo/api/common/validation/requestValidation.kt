package com.example.demo.api.common.validation

import com.example.demo.api.common.BadRequestException
import org.springframework.validation.DataBinder
import org.springframework.validation.Validator

fun <T : Any> notNull(value: T?, field: String): T {
    return value ?: throw BadRequestException("field=$field must be not null")
}

fun notBlank(value: String, field: String): String {
    return if (value.isBlank()) {
        throw BadRequestException("$field must not be blank!")
    } else value
}

fun <T : Any> Validator.validateRequest(value: T, beanName: String?): T {
    return validateBean(validator = this, value = value, beanName = beanName)
}

fun <T : Any> validateBean(validator: Validator, value: T, beanName: String?): T {
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

    val classname: String = value::class.qualifiedName ?: value::class.toString()

    val msg = "Failed to validate bean=$classname objectName: ${result.objectName} nestedPath: ${result.nestedPath} allErrors:${errors.joinToString("\n")}"

    throw BadRequestException(msg)
}
