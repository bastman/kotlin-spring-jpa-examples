package com.example.demo.api.realestate

import com.example.demo.api.realestate.handler.common.response.PropertyResponse
import com.example.demo.api.realestate.handler.create.CreatePropertyHandler
import com.example.demo.api.realestate.handler.create.CreatePropertyRequest
import com.example.demo.api.realestate.handler.duplicates.ListDuplicatePropertiesHandler
import com.example.demo.api.realestate.handler.duplicates.ListDuplicatePropertiesResponse
import com.example.demo.api.realestate.handler.getbyid.GetPropertyByIdHandler
import com.example.demo.api.realestate.handler.join_cluster.JoinPropertyClusterHandler
import com.example.demo.api.realestate.handler.join_cluster.JoinPropertyClusterRequest
import com.example.demo.api.realestate.handler.join_cluster.JoinPropertyClusterResponse
import com.example.demo.api.realestate.handler.link.LinkPropertiesHandler
import com.example.demo.api.realestate.handler.link.LinkPropertiesRequest
import com.example.demo.api.realestate.handler.link.LinkPropertiesResponse
import com.example.demo.api.realestate.handler.linked_by.PropertyLinkedByHandler
import com.example.demo.api.realestate.handler.linked_by.PropertyLinkedByResponse
import com.example.demo.api.realestate.handler.links_to.PropertyLinksToHandler
import com.example.demo.api.realestate.handler.links_to.PropertyLinksToResponse
import com.example.demo.api.realestate.handler.search.SearchPropertiesHandler
import com.example.demo.api.realestate.handler.search.SearchPropertiesRequest
import com.example.demo.api.realestate.handler.search.SearchPropertiesResponse
import com.example.demo.api.realestate.handler.split_cluster.SplitPropertyClusterHandler
import com.example.demo.api.realestate.handler.split_cluster.SplitPropertyClusterRequest
import com.example.demo.api.realestate.handler.split_cluster.SplitPropertyClusterResponse
import com.example.demo.api.realestate.handler.unlink.UnlinkPropertiesHandler
import com.example.demo.api.realestate.handler.unlink.UnlinkPropertiesRequest
import com.example.demo.api.realestate.handler.unlink.UnlinkPropertiesResponse
import com.example.demo.api.realestate.handler.update.UpdatePropertyHandler
import com.example.demo.api.realestate.handler.update.UpdatePropertyRequest
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = arrayOf("*"))
class PropertiesController(
        private val createHandler: CreatePropertyHandler,
        private val updateHandler: UpdatePropertyHandler,
        private val getByIdHandler: GetPropertyByIdHandler,
        private val searchHandler: SearchPropertiesHandler,
        private val linkHandler: LinkPropertiesHandler,
        private val unlinkHandler: UnlinkPropertiesHandler,
        private val listDuplicatesHandler: ListDuplicatePropertiesHandler,
        private val linksToHandler: PropertyLinksToHandler,
        private val linkedByHandler: PropertyLinkedByHandler,
        private val joinClusterHandler: JoinPropertyClusterHandler,
        private val splitClusterHandler: SplitPropertyClusterHandler
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

    @PostMapping("/properties/{propertyId}/cluster/join-to")
    fun joinCluster(
            @PathVariable propertyId: UUID,
            @RequestBody request:JoinPropertyClusterRequest
    ): JoinPropertyClusterResponse =
            joinClusterHandler.handle(propertyId, request)

    @PostMapping("/properties/cluster/split")
    fun splitCluster(@RequestBody request:SplitPropertyClusterRequest): SplitPropertyClusterResponse =
            splitClusterHandler.handle(request)

}