package com.example.demo.api.realestate.handler.update

import com.example.demo.api.common.validation.validateRequest
import com.example.demo.api.realestate.domain.JpaPropertyService
import com.example.demo.api.realestate.domain.Property
import com.example.demo.api.realestate.domain.PropertyAddress
import com.example.demo.api.realestate.handler.common.response.PropertyResponse
import com.example.demo.util.fp.pipe
import org.springframework.stereotype.Component
import org.springframework.validation.Validator
import java.util.*

@Component
class UpdatePropertyHandler(
        private val validator: Validator,
        private val jpaPropertyService: JpaPropertyService
) {

    fun handle(propertyId: UUID, request: UpdatePropertyRequest): PropertyResponse =
            execute(
                    propertyId = propertyId,
                    request = validator.validateRequest(request, "request")
            )

    private fun execute(propertyId: UUID, request: UpdatePropertyRequest): PropertyResponse {
        val property = jpaPropertyService.getById(propertyId)
                .copyWithUpdateRequest(request)

        return PropertyResponse.of(
                property = jpaPropertyService.update(property)
        )
    }
}

private fun Property.copyWithUpdateRequest(req: UpdatePropertyRequest): Property {
    return this.pipe {
        if (req.type != null) it.copy(type = req.type) else it
    }.pipe {
        if (req.name != null) it.copy(name = req.name.trim()) else it
    }.pipe {
        if (req.address != null) {
            it.copy(address = it.address.copyWithUpdateRequest(req.address))
        } else {
            it
        }
    }
}


private fun PropertyAddress.copyWithUpdateRequest(
        req: UpdatePropertyRequest.UpdatePropertyAddressRequest
): PropertyAddress {
    return this.pipe {
        if (req.country != null) it.copy(country = req.country.trim()) else it
    }.pipe {
        if (req.city != null) it.copy(city = req.city.trim()) else it
    }.pipe {
        if (req.zip != null) it.copy(zip = req.zip.trim()) else it
    }.pipe {
        if (req.street != null) it.copy(street = req.street.trim()) else it
    }.pipe {
        if (req.number != null) it.copy(number = req.number.trim()) else it
    }.pipe {
        if (req.district != null) it.copy(district = req.district.trim()) else it
    }.pipe {
        if (req.neighborhood != null) it.copy(neighborhood = req.neighborhood.trim()) else it
    }
}