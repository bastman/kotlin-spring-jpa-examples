package com.example.demo.api.realestate

import com.example.demo.api.realestate.handler.duplicates.ListDuplicatePropertiesHandler
import com.example.demo.api.realestate.handler.duplicates.ListDuplicatePropertiesResponse
import com.example.demo.api.realestate.handler.link.LinkPropertiesHandler
import com.example.demo.api.realestate.handler.link.LinkPropertiesRequest
import com.example.demo.api.realestate.handler.link.LinkPropertiesResponse
import com.example.demo.api.realestate.handler.linked_by.PropertyLinkedByHandler
import com.example.demo.api.realestate.handler.linked_by.PropertyLinkedByResponse
import com.example.demo.api.realestate.handler.links_to.PropertyLinksToHandler
import com.example.demo.api.realestate.handler.links_to.PropertyLinksToResponse
import com.example.demo.api.realestate.handler.unlink.UnlinkPropertiesHandler
import com.example.demo.api.realestate.handler.unlink.UnlinkPropertiesRequest
import com.example.demo.api.realestate.handler.unlink.UnlinkPropertiesResponse
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