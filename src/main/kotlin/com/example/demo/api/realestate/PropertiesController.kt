package com.example.demo.api.realestate

import com.example.demo.api.realestate.handler.create.CreatePropertyHandler
import com.example.demo.api.realestate.handler.create.CreatePropertyRequest
import com.example.demo.api.realestate.handler.getbyid.GetPropertyByIdHandler
import com.example.demo.api.realestate.handler.search.SearchPropertiesHandler
import com.example.demo.api.realestate.handler.search.SearchPropertiesRequest
import com.example.demo.api.realestate.handler.search.SearchPropertiesResponse
import com.example.demo.api.realestate.handler.update.UpdatePropertyHandler
import com.example.demo.api.realestate.handler.update.UpdatePropertyRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class PropertiesController(
        private val createHandler: CreatePropertyHandler,
        private val updateHandler: UpdatePropertyHandler,
        private val getByIdHandler: GetPropertyByIdHandler,
        private val searchHandler: SearchPropertiesHandler
) {
    @GetMapping("/properties/{propertyId}")
    fun getById(
            @PathVariable propertyId: UUID
    ): Any? {
        return getByIdHandler.handle(propertyId)
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

    @PostMapping("/properties/search", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun search(@RequestBody request: SearchPropertiesRequest): SearchPropertiesResponse {
        return searchHandler.handle(request)
    }
}