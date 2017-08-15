package com.example.demo.api.realestate.handler.properties.crud.create

import com.example.demo.api.common.validation.validateRequest
import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.domain.jpa.entities.PropertyAddress
import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyService
import com.example.demo.api.realestate.handler.common.response.PropertyResponse
import org.springframework.stereotype.Component
import org.springframework.validation.Validator
import java.time.Instant
import java.util.*

@Component
class CreatePropertyHandler(
        private val validator: Validator,
        private val jpaPropertyService: JpaPropertyService
) {

    fun handle(request: CreatePropertyRequest): PropertyResponse =
            execute(validator.validateRequest(request, "request"))

    private fun execute(request: CreatePropertyRequest): PropertyResponse {
        val newPropertyId = UUID.randomUUID()
        val property = Property(
                id = newPropertyId,
                created = Instant.now(),
                modified = Instant.now(),
                clusterId = null,
                type = request.type,
                name = request.name,
                address = PropertyAddress(
                        country = request.address.country.trim(),
                        city = request.address.city.trim(),
                        zip = request.address.zip.trim(),
                        street = request.address.street.trim(),
                        number = request.address.number.trim(),
                        district = request.address.district.trim(),
                        neighborhood = request.address.neighborhood.trim()
                )
        )

        return PropertyResponse.of(
                property = jpaPropertyService.insert(property)
        )
    }
}
