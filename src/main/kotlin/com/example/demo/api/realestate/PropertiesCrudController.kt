package com.example.demo.api.realestate

import com.example.demo.api.realestate.handler.common.response.PropertyResponse
import com.example.demo.api.realestate.handler.create.CreatePropertyHandler
import com.example.demo.api.realestate.handler.create.CreatePropertyRequest
import com.example.demo.api.realestate.handler.getbyid.GetPropertyByIdHandler
import com.example.demo.api.realestate.handler.search.SearchPropertiesHandler
import com.example.demo.api.realestate.handler.search.SearchPropertiesRequest
import com.example.demo.api.realestate.handler.search.SearchPropertiesResponse
import com.example.demo.api.realestate.handler.update.UpdatePropertyHandler
import com.example.demo.api.realestate.handler.update.UpdatePropertyRequest
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = arrayOf("*"))
class PropertiesCrudController(
        private val createHandler: CreatePropertyHandler,
        private val updateHandler: UpdatePropertyHandler,
        private val getByIdHandler: GetPropertyByIdHandler,
        private val searchHandler: SearchPropertiesHandler
) {
    @GetMapping("/properties/{propertyId}")
    fun getById(@PathVariable propertyId: UUID): Any? {
        return getByIdHandler.handle(propertyId)
    }

    @PostMapping("/properties")
    fun create(@RequestBody request: CreatePropertyRequest): PropertyResponse =
            createHandler.handle(request)

    @PostMapping("/properties/{propertyId}")
    fun update(@PathVariable propertyId: UUID, @RequestBody request: UpdatePropertyRequest): PropertyResponse =
            updateHandler.handle(propertyId, request)

    @PostMapping("/properties/search")
    fun search(@RequestBody request: SearchPropertiesRequest): SearchPropertiesResponse =
            searchHandler.handle(request)
}