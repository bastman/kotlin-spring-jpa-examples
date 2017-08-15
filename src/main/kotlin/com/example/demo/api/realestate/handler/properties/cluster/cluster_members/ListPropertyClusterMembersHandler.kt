package com.example.demo.api.realestate.handler.properties.cluster.cluster_members

import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.domain.jpa.entities.PropertyCluster
import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyClusterService
import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyService
import com.example.demo.api.realestate.handler.common.response.PropertyDto
import org.springframework.stereotype.Component
import java.util.*

@Component
class ListPropertyClusterMembersHandler(
        private val jpaPropertyClusterService: JpaPropertyClusterService,
        private val jpaPropertyService: JpaPropertyService
) {

    fun handle(propertyId: UUID): PropertyClusterMembersResponse {
        val property: Property = jpaPropertyService.getById(propertyId)
        val clusterId: UUID? = property.clusterId
        val cluster: PropertyCluster? = if (clusterId != null) {
            jpaPropertyClusterService.findById(property.clusterId)
        } else {
            null
        }

        val clusterMembers: List<Property> = if (clusterId != null) {
            jpaPropertyService.findByClusterId(clusterId)
        } else {
            emptyList()
        }

        return PropertyClusterMembersResponse(
                cluster = cluster,
                properties = clusterMembers.map { PropertyDto.of(it) }
        )
    }
}