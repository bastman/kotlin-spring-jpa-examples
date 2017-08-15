package com.example.demo.api.realestate.handler.properties.cluster.create_cluster

import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.handler.common.response.PropertyDto
import java.util.*

data class CreatePropertyClusterResponse(
        val _debug_clusters: Any,
        val properties: List<PropertyDto>
) {
    companion object {
        fun of(properties: List<Property>): CreatePropertyClusterResponse {
            return CreatePropertyClusterResponse(
                    properties = properties.map { PropertyDto.of(it) },
                    _debug_clusters = properties
                            .map {
                                PropertySummary(
                                        id = it.id,
                                        clusterId = it.clusterId,
                                        name = it.name
                                )
                            }
                            .groupBy { it.clusterId ?: "null" }
            )
        }
    }

    data class PropertySummary(
            val id: UUID,
            val clusterId: UUID?,
            val name: String
    )
}
