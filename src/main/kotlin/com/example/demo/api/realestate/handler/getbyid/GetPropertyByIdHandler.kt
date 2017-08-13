package com.example.demo.api.realestate.handler.getbyid

import com.example.demo.api.realestate.domain.JpaPropertyService
import org.springframework.stereotype.Component
import java.util.*

@Component
class GetPropertyByIdHandler(
        private val jpaPropertyService: JpaPropertyService
) {

    fun handle(propertyId:UUID) = jpaPropertyService.getById(propertyId)
}