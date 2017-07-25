package com.example.demo.logging

/**
 * see: https://stackoverflow.com/questions/34416869/idiomatic-way-of-logging-in-kotlin
 */
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject


object AppLogger {
    fun get(clazz: Class<*>): Logger {
        return LoggerFactory.getLogger(unwrapCompanionClass(clazz).name)
    }

    fun get(clazz: KClass<*>): Logger = get(clazz.java)
    operator fun invoke(clazz: Class<*>): Logger = get(clazz)
    operator fun invoke(clazz: KClass<*>): Logger = get(clazz)

    // unwrap companion class to enclosing class given a Java Class
    private fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
        return if (ofClass.enclosingClass != null && ofClass.enclosingClass.kotlin.companionObject?.java == ofClass) {
            ofClass.enclosingClass
        } else {
            ofClass
        }
    }
}