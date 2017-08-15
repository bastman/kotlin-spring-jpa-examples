package com.example.demo.api.realestate.handler.properties.cluster.join_cluster

import com.example.demo.api.realestate.domain.jpa.entities.PropertyCluster
import com.example.demo.api.realestate.handler.common.response.PropertyDto

data class JoinPropertyClusterResponse(
        val cluster: PropertyCluster,
        val property: PropertyDto,
        val joinToProperty: PropertyDto
)