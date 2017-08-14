package com.example.demo.api.realestate

import com.example.demo.api.realestate.handler.create.CreatePropertyHandler
import com.example.demo.api.realestate.handler.create.CreatePropertyRequest
import com.example.demo.api.realestate.handler.duplicates.ListDuplicatePropertiesHandler
import com.example.demo.api.realestate.handler.duplicates.ListDuplicatePropertiesResponse
import com.example.demo.api.realestate.handler.getbyid.GetPropertyByIdHandler
import com.example.demo.api.realestate.handler.link.LinkPropertiesHandler
import com.example.demo.api.realestate.handler.link.LinkPropertiesRequest
import com.example.demo.api.realestate.handler.link.LinkPropertiesResponse
import com.example.demo.api.realestate.handler.search.SearchPropertiesHandler
import com.example.demo.api.realestate.handler.search.SearchPropertiesRequest
import com.example.demo.api.realestate.handler.search.SearchPropertiesResponse
import com.example.demo.api.realestate.handler.unlink.UnlinkPropertiesHandler
import com.example.demo.api.realestate.handler.unlink.UnlinkPropertiesRequest
import com.example.demo.api.realestate.handler.unlink.UnlinkPropertiesResponse
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
        private val searchHandler: SearchPropertiesHandler,
        private val linkHandler: LinkPropertiesHandler,
        private val unlinkHandler: UnlinkPropertiesHandler,
        private val listDuplicatesHandler: ListDuplicatePropertiesHandler
) {
    @GetMapping("/properties/{propertyId}")
    fun getById(@PathVariable propertyId: UUID): Any? {
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

    @PostMapping("/properties/link", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun link(@RequestBody request: LinkPropertiesRequest): LinkPropertiesResponse {
        return linkHandler.handle(request)
    }

    @PostMapping("/properties/unlink", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun unlink(@RequestBody request: UnlinkPropertiesRequest): UnlinkPropertiesResponse {
        return unlinkHandler.handle(request)
    }

    @GetMapping("/properties/{propertyId}/duplicates")
    fun listDuplicates(@PathVariable propertyId: UUID): ListDuplicatePropertiesResponse {
        return listDuplicatesHandler.handle(propertyId)
    }
}