package com.example.demo.api.realestate

import com.example.demo.api.realestate.domain.JpaPropertyService
import com.example.demo.api.realestate.handler.create.CreatePropertyHandler
import com.example.demo.api.realestate.handler.create.CreatePropertyRequest
import com.example.demo.api.realestate.handler.update.UpdatePropertyHandler
import com.example.demo.api.realestate.handler.update.UpdatePropertyRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class PropertiesController(
        private val jpaPropertyService: JpaPropertyService,
        private val createHandler: CreatePropertyHandler,
        private val updateHandler: UpdatePropertyHandler
) {
    @GetMapping("/properties/{propertyId}")
    fun getById(
            @PathVariable propertyId: UUID
    ): Any? {
        return jpaPropertyService.getById(propertyId)
    }

    @PostMapping("/properties", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun create(@RequestBody request: CreatePropertyRequest): Any? {
        return createHandler.handle(request)
    }

    @PostMapping("/properties/{propertyId}", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun update(
            @PathVariable propertyId: UUID,
            @RequestBody request: UpdatePropertyRequest
    ): Any? {
        return updateHandler.handle(propertyId, request)
    }
}