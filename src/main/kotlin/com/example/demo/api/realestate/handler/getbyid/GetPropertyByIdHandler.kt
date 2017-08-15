package com.example.demo.api.realestate.handler.getbyid

import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyService
import com.example.demo.api.realestate.handler.common.response.PropertyResponse
import org.springframework.stereotype.Component
import java.util.*

@Component
class GetPropertyByIdHandler(private val jpaPropertyService: JpaPropertyService) {

    fun handle(propertyId: UUID): PropertyResponse =
            PropertyResponse.of(property = jpaPropertyService.getById(propertyId))
}