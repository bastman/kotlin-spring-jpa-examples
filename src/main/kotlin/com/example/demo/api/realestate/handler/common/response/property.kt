package com.example.demo.api.realestate.handler.common.response


import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.domain.jpa.entities.PropertyAddress
import com.example.demo.api.realestate.domain.jpa.entities.PropertyType
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant
import java.util.*

data class PropertyResponse(val property: PropertyDto) {
    companion object {
        fun of(property: Property): PropertyResponse = PropertyResponse(property = property.toDto())
    }
}

data class PropertiesResponse(
        @JsonInclude(JsonInclude.Include.NON_NULL) val paging: ResponsePaging?,
        val properties: List<PropertyDto>
) {
    companion object {
        fun of(properties: List<Property>): PropertiesResponse =
                PropertiesResponse(
                        properties = properties.map { it.toDto() },
                        paging = null
                )
    }
}

data class PropertyDto(
        val id: UUID,
        val version: Int,
        val createdAt: Instant,
        val modified: Instant,

        val type: PropertyType,
        val name: String,
        val address: PropertyAddressDto
) {
    companion object {
        fun of(source: Property): PropertyDto {
            return PropertyDto(
                    id = source.id,
                    version = source.version,
                    createdAt = source.getCreatedAt(),
                    modified = source.getModifiedAt(),
                    type = source.type,
                    name = source.name,
                    address = PropertyAddressDto.of(source.address)
            )
        }
    }
}

data class PropertyAddressDto(
        val country: String,
        val city: String,
        val zip: String,
        val street: String,
        val number: String,
        val district: String,
        val neighborhood: String
) {
    companion object {
        fun of(source: PropertyAddress): PropertyAddressDto {
            return PropertyAddressDto(
                    country = source.country,
                    city = source.city,
                    zip = source.zip,
                    street = source.street,
                    number = source.number,
                    district = source.district,
                    neighborhood = source.neighborhood
            )
        }
    }
}

private fun Property.toDto() = PropertyDto.of(this)