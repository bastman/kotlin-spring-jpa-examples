package com.example.demo.api.realestate.handler.properties.cluster.create_cluster

import org.hibernate.validator.constraints.NotEmpty
import java.util.*

data class CreatePropertyClusterRequest(
        @get:NotEmpty val propertyIds:List<UUID>
)