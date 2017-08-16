package com.example.demo.api.realestate.routing.properties

import com.example.demo.api.realestate.handler.properties.links.create_links.LinkPropertiesHandler
import com.example.demo.api.realestate.handler.properties.links.create_links.LinkPropertiesRequest
import com.example.demo.api.realestate.handler.properties.links.create_links.LinkPropertiesResponse
import com.example.demo.api.realestate.handler.properties.links.duplicates.ListDuplicatePropertiesHandler
import com.example.demo.api.realestate.handler.properties.links.duplicates.ListDuplicatePropertiesResponse
import com.example.demo.api.realestate.handler.properties.links.linked_by.PropertyLinkedByHandler
import com.example.demo.api.realestate.handler.properties.links.linked_by.PropertyLinkedByResponse
import com.example.demo.api.realestate.handler.properties.links.linked_to.PropertyLinksToHandler
import com.example.demo.api.realestate.handler.properties.links.linked_to.PropertyLinksToResponse
import com.example.demo.api.realestate.handler.properties.links.unlink.UnlinkPropertiesHandler
import com.example.demo.api.realestate.handler.properties.links.unlink.UnlinkPropertiesRequest
import com.example.demo.api.realestate.handler.properties.links.unlink.UnlinkPropertiesResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = arrayOf("*"))
class PropertiesLinksController(
        private val linkHandler: LinkPropertiesHandler,
        private val unlinkHandler: UnlinkPropertiesHandler,
        private val listDuplicatesHandler: ListDuplicatePropertiesHandler,
        private val linksToHandler: PropertyLinksToHandler,
        private val linkedByHandler: PropertyLinkedByHandler
) {
    @PostMapping("/properties/link")
    fun link(@RequestBody request: LinkPropertiesRequest): LinkPropertiesResponse =
            linkHandler.handle(request)

    @PostMapping("/properties/unlink")
    fun unlink(@RequestBody request: UnlinkPropertiesRequest): UnlinkPropertiesResponse =
            unlinkHandler.handle(request)

    @GetMapping("/properties/{propertyId}/links-to")
    fun getLinksTo(@PathVariable propertyId: UUID): PropertyLinksToResponse =
            linksToHandler.handle(propertyId)

    @GetMapping("/properties/{propertyId}/linked-by")
    fun getLinkedBy(@PathVariable propertyId: UUID): PropertyLinkedByResponse =
            linkedByHandler.handle(propertyId)

    @GetMapping("/properties/{propertyId}/duplicates")
    fun listDuplicates(@PathVariable propertyId: UUID): ListDuplicatePropertiesResponse =
            listDuplicatesHandler.handle(propertyId)
}