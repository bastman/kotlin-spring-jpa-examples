package com.example.demo.api.realestate.handler.properties.links.linked_to

import com.example.demo.api.realestate.handler.common.response.PropertyDto

data class PropertyLinksToResponse(
        val linksTo: List<PropertyDto>,
        val property: PropertyDto
)