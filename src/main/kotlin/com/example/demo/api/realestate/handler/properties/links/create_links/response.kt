package com.example.demo.api.realestate.handler.properties.links.create_links

import com.example.demo.api.realestate.domain.jpa.entities.PropertyLink

data class LinkPropertiesResponse(
        val links: List<PropertyLink>
)