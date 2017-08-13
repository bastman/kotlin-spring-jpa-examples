package com.example.demo.api.realestate.handler.unlink


import com.example.demo.api.common.BadRequestException
import java.util.*

data class UnlinkPropertiesRequest(
        val propertyId1: UUID,
        val propertyId2: UUID
) {
    fun validated(): UnlinkPropertiesRequest {
        if (propertyId1 == propertyId2) {
            throw BadRequestException("request.propertyId1 must not equal request.propertyId2 !")
        }
        return this
    }
}