package com.example.demo.api.realestate.handler.properties.crud.update

import com.example.demo.api.common.validation.annotations.NotBlankOrNull
import com.example.demo.api.realestate.domain.jpa.entities.PropertyType
import io.swagger.annotations.ApiModel
import javax.validation.Valid

data class UpdatePropertyRequest(
        val type: PropertyType?,
        @get: [NotBlankOrNull] val name: String?,
        @get: [Valid] val address: UpdatePropertyAddressRequest?

) {
    @ApiModel("UpdatePropertyRequest.PropertyAddress")
    data class UpdatePropertyAddressRequest(
            @get: [NotBlankOrNull] val country: String?,
            @get: [NotBlankOrNull] val city: String?,
            val zip: String?,
            val street: String?,
            val number: String?,
            val district: String?,
            val neighborhood: String?
    )
}

