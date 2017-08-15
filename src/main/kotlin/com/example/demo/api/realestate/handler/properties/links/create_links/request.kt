package com.example.demo.api.realestate.handler.properties.links.create_links

import com.example.demo.api.common.BadRequestException
import java.util.*

data class LinkPropertiesRequest(
        val propertyId1: UUID,
        val propertyId2: UUID
) {
    fun validated(): LinkPropertiesRequest {
        if (propertyId1 == propertyId2) {
            throw BadRequestException("request.propertyId1 must not equal request.propertyId2 !")
        }
        return this
    }
}
