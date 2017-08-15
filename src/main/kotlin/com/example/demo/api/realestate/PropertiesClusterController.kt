package com.example.demo.api.realestate

import com.example.demo.api.realestate.handler.cluster_members.ListPropertyClusterMembersHandler
import com.example.demo.api.realestate.handler.cluster_members.PropertyClusterMembersResponse
import com.example.demo.api.realestate.handler.join_cluster.JoinPropertyClusterHandler
import com.example.demo.api.realestate.handler.join_cluster.JoinPropertyClusterRequest
import com.example.demo.api.realestate.handler.join_cluster.JoinPropertyClusterResponse
import com.example.demo.api.realestate.handler.split_cluster.SplitPropertyClusterHandler
import com.example.demo.api.realestate.handler.split_cluster.SplitPropertyClusterRequest
import com.example.demo.api.realestate.handler.split_cluster.SplitPropertyClusterResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = arrayOf("*"))
class PropertiesClusterController(
        private val joinClusterHandler: JoinPropertyClusterHandler,
        private val splitClusterHandler: SplitPropertyClusterHandler,
        private val listClusterMembersHandler: ListPropertyClusterMembersHandler
) {

    @PostMapping("/properties/{propertyId}/cluster/join-to")
    fun joinCluster(
            @PathVariable propertyId: UUID,
            @RequestBody request: JoinPropertyClusterRequest
    ): JoinPropertyClusterResponse =
            joinClusterHandler.handle(propertyId, request)

    @GetMapping("/properties/{propertyId}/cluster/members")
    fun findClusterMembers(
            @PathVariable propertyId: UUID
    ): PropertyClusterMembersResponse =
            listClusterMembersHandler.handle(propertyId)

    @PostMapping("/properties/cluster/split")
    fun splitCluster(@RequestBody request: SplitPropertyClusterRequest): SplitPropertyClusterResponse =
            splitClusterHandler.handle(request)

}