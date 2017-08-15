package com.example.demo.api.realestate.handler.properties.links.unlink


import com.example.demo.api.common.BadRequestException
import java.util.*

data class UnlinkPropertiesRequest(
        val fromPropertyId: UUID,
        val toPropertyId: UUID
) {
    fun validated(): UnlinkPropertiesRequest {
        if (fromPropertyId == toPropertyId) {
            throw BadRequestException("request.propertyId1 must not equal request.propertyId2 !")
        }
        return this
    }
}