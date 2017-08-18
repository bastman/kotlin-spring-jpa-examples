package com.example.demo.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.ISO8601DateFormat
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {

    @Bean
    fun om(): ObjectMapper = defaultMapper()

    companion object {
        private val DATE_FORMAT_ISO8601 = ISO8601DateFormat()
        fun defaultMapper(): ObjectMapper {
            return jacksonObjectMapper()
                    .findAndRegisterModules()
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .setDateFormat(DATE_FORMAT_ISO8601)
            // .disable(DeserializationFeature.)
        }
    }


}