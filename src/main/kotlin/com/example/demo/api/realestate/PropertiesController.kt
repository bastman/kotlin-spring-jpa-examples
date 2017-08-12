package com.example.demo.api.realestate

import com.example.demo.api.realestate.handler.create.CreatePropertyHandler
import com.example.demo.api.realestate.handler.create.CreatePropertyRequest
import com.example.demo.api.tweeter.AuthorController
import com.example.demo.api.tweeter.domain.Author
import com.example.demo.util.fp.pipe
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
class PropertiesController(
        private val createHandler:CreatePropertyHandler
) {

    @PostMapping("/properties", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun create(@RequestBody request: CreatePropertyRequest): Any? {
        return createHandler.handle(request)
    }
}