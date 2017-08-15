package com.example.demo.api.realestate.handler.split_cluster

import org.hibernate.validator.constraints.NotEmpty
import java.util.*

data class SplitPropertyClusterRequest(
        @get:NotEmpty val propertyIds: List<UUID>
)