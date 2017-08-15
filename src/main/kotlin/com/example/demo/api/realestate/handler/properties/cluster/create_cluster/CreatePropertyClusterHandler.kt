package com.example.demo.api.realestate.handler.properties.cluster.create_cluster

import com.example.demo.api.common.validation.validateRequest
import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.domain.jpa.entities.PropertyCluster
import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyClusterService
import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyService
import org.springframework.stereotype.Component
import org.springframework.validation.Validator
import java.time.Instant
import java.util.*

@Component
class CreatePropertyClusterHandler(
        private val validator: Validator,
        private val jpaPropertyService: JpaPropertyService,
        private val jpaClusterService: JpaPropertyClusterService
) {

    fun handle(request: CreatePropertyClusterRequest): CreatePropertyClusterResponse {
        val properties: List<Property> = execute(propertyIds = validator
                .validateRequest(request, "request")
                .propertyIds
        )

        return CreatePropertyClusterResponse.of(properties = properties)
    }

    private fun execute(propertyIds: List<UUID>): List<Property> {
        val properties: List<Property> = jpaPropertyService
                .findByIdList(propertyIdList = propertyIds.distinct())

        if (properties.size <= 1) {
            // special case:  properties contains no more than one item
            // -> no new cluster, just reset property.clusterId to null
            return properties.map {
                jpaPropertyService.update(
                        property = it.copy(clusterId = null)
                )
            }
        }

        // default case: create a new cluster and assign all properties to it
        val newCluster = PropertyCluster(
                id = UUID.randomUUID(),
                created = Instant.now(),
                modified = Instant.now()
        )
        val savedClusterId: UUID = jpaClusterService
                .insert(newCluster)
                .id

        val savedProperties = properties.map {
            jpaPropertyService.update(
                    property = it.copy(clusterId = savedClusterId)
            )
        }

        return savedProperties
    }
}