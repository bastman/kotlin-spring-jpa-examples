package com.example.demo.api.realestate.handler.join_cluster

import com.example.demo.api.common.BadRequestException
import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.domain.jpa.entities.PropertyCluster
import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyClusterService
import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyService
import com.example.demo.api.realestate.handler.common.response.PropertyDto
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class JoinPropertyClusterHandler(
        private val jpaPropertyService: JpaPropertyService,
        private val jpaPropertyClusterService: JpaPropertyClusterService
) {

    fun handle(propertyId: UUID, request: JoinPropertyClusterRequest): JoinPropertyClusterResponse {
        val joinToPropertyId = request.joinToPropertyId
        if (joinToPropertyId == propertyId) {
            throw BadRequestException(
                    "request.propertyId must not equal request.joinToPropertyId !"
            )
        }

        return execute(
                property = jpaPropertyService.getById(propertyId),
                joinToProperty = jpaPropertyService.getById(joinToPropertyId)
        )
    }

    private fun execute(property: Property, joinToProperty: Property): JoinPropertyClusterResponse {
        val sharedClusterId: UUID? = joinToProperty.clusterId ?: property.clusterId

        // case: both properties are not related to any cluster -> create a new one
        val sharedCluster: PropertyCluster = if (sharedClusterId == null) {
            createNewClusterAndSave()
        } else {
            jpaPropertyClusterService.getById(sharedClusterId)
        }

        val savedProperty = applyClusterIdToPropertyIfChangedAndSave(
                property = property, newClusterId = sharedCluster.id
        )
        val savedJoinToProperty = applyClusterIdToPropertyIfChangedAndSave(
                property = joinToProperty, newClusterId = sharedCluster.id
        )

        return JoinPropertyClusterResponse(
                cluster = sharedCluster,
                property = PropertyDto.of(savedProperty),
                joinToProperty = PropertyDto.of(savedJoinToProperty)
        )
    }

    private fun createNewClusterAndSave(): PropertyCluster {
        val newCluster = PropertyCluster(
                id = UUID.randomUUID(),
                created = Instant.now(),
                modified = Instant.now()
        )

        return jpaPropertyClusterService.insert(newCluster)
    }

    private fun applyClusterIdToPropertyIfChangedAndSave(property: Property, newClusterId: UUID): Property {
        if (property.clusterId == newClusterId) {
            // nothing changed
            return property
        }

        return jpaPropertyService.update(
                property.copy(clusterId = newClusterId)
        )
    }
}
