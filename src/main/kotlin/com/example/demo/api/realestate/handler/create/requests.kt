package com.example.demo.api.realestate.handler.create

import com.example.demo.api.realestate.domain.PropertyType
import io.swagger.annotations.ApiModel
import org.hibernate.validator.constraints.NotBlank
import javax.validation.Valid

data class CreatePropertyRequest(
        val type: PropertyType,
        @get: [NotBlank] val name: String,
        @get: [Valid] val address: PropertyAddress

) {
    @ApiModel("CreatePropertyRequest.PropertyAddress")
    data class PropertyAddress(
            val country: String,
            val city: String,
            val zip: String,
            val street: String,
            val number: String,
            val district: String,
            val neighborhood: String
    )
}

