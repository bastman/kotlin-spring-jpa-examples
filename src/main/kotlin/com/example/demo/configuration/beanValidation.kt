package com.example.demo.configuration

import org.hibernate.validator.HibernateValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor


@Configuration
class BeanValidationConfig {

    @Bean
    fun methodValidationPostProcessor(): MethodValidationPostProcessor {
        val mvProcessor = MethodValidationPostProcessor()
        mvProcessor.setValidator(validator())
        return mvProcessor
    }

    //@Bean
    //fun validator() = LocalValidatorFactoryBean()

    @Bean
    fun validator(): LocalValidatorFactoryBean {
        val validator = LocalValidatorFactoryBean()
        validator.setProviderClass(HibernateValidator::class.java)
        validator.afterPropertiesSet()
        return validator
    }
}