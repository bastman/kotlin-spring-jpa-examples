package com.example.demo.api.realestate.handler.brokers.crud.update

import com.example.demo.api.common.validation.annotations.EmailOrNull
import com.example.demo.api.common.validation.annotations.NotBlankOrNull
import io.swagger.annotations.ApiModel
import javax.validation.Valid

data class UpdateBrokerRequest(
        @get: [EmailOrNull] val email: String?,
        val companyName: String?,
        val firstName: String?,
        val lastName: String?,
        val phoneNumber: String?,
        val comment: String?,
        @get: [Valid] val address: UpdateBrokerAddress?
) {
    @ApiModel("UpdateBrokerRequest.BrokerAddress")
    data class UpdateBrokerAddress(
            @get: [NotBlankOrNull] val country: String?,
            @get: [NotBlankOrNull] val city: String?,
            val state: String?,
            val street: String?,
            val number: String?
    )
}