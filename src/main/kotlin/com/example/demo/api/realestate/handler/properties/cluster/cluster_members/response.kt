package com.example.demo.api.realestate.handler.properties.cluster.cluster_members

import com.example.demo.api.realestate.domain.jpa.entities.PropertyCluster
import com.example.demo.api.realestate.handler.common.response.PropertyDto

data class PropertyClusterMembersResponse(
        val cluster: PropertyCluster?,
        val properties: List<PropertyDto>
)
