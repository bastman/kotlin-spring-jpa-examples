package com.example.demo.api.realestate.handler.duplicates

import com.example.demo.api.realestate.domain.jpa.entities.Property
import java.util.*

data class ListDuplicatePropertiesResponse(
        val linksTo: List<UUID>,
        val linksFrom: List<UUID>,
        val duplicateProperties: List<Property>,
        val property: Property
)