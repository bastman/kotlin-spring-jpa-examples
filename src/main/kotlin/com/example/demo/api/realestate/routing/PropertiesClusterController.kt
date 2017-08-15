package com.example.demo.api.realestate.routing

import com.example.demo.api.realestate.handler.properties.cluster.cluster_members.ListPropertyClusterMembersHandler
import com.example.demo.api.realestate.handler.properties.cluster.cluster_members.PropertyClusterMembersResponse
import com.example.demo.api.realestate.handler.properties.cluster.create_cluster.CreatePropertyClusterHandler
import com.example.demo.api.realestate.handler.properties.cluster.create_cluster.CreatePropertyClusterRequest
import com.example.demo.api.realestate.handler.properties.cluster.create_cluster.CreatePropertyClusterResponse
import com.example.demo.api.realestate.handler.properties.cluster.join_cluster.JoinPropertyClusterHandler
import com.example.demo.api.realestate.handler.properties.cluster.join_cluster.JoinPropertyClusterRequest
import com.example.demo.api.realestate.handler.properties.cluster.join_cluster.JoinPropertyClusterResponse
import com.example.demo.api.realestate.handler.properties.cluster.split_cluster.SplitPropertyClusterHandler
import com.example.demo.api.realestate.handler.properties.cluster.split_cluster.SplitPropertyClusterRequest
import com.example.demo.api.realestate.handler.properties.cluster.split_cluster.SplitPropertyClusterResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = arrayOf("*"))
class PropertiesClusterController(
        private val joinClusterHandler: JoinPropertyClusterHandler,
        private val splitClusterHandler: SplitPropertyClusterHandler,
        private val listClusterMembersHandler: ListPropertyClusterMembersHandler,
        private val createClusterHandler: CreatePropertyClusterHandler
) {
    @PostMapping("/properties/cluster/create")
    fun createCluster(@RequestBody request: CreatePropertyClusterRequest): CreatePropertyClusterResponse =
            createClusterHandler.handle(request)

    @PostMapping("/properties/cluster/split")
    fun splitCluster(@RequestBody request: SplitPropertyClusterRequest): SplitPropertyClusterResponse =
            splitClusterHandler.handle(request)

    @GetMapping("/properties/{propertyId}/cluster/members")
    fun findClusterMembers(@PathVariable propertyId: UUID): PropertyClusterMembersResponse =
            listClusterMembersHandler.handle(propertyId)

    @PostMapping("/properties/{propertyId}/cluster/join-to")
    fun joinCluster(
            @PathVariable propertyId: UUID,
            @RequestBody request: JoinPropertyClusterRequest
    ): JoinPropertyClusterResponse =
            joinClusterHandler.handle(propertyId, request)
}