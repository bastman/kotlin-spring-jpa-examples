package com.example.demo.api.realestate.handler.brokers.crud.create

import io.swagger.annotations.ApiModel
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import javax.validation.Valid

data class CreateBrokerRequest(
        @get: [Email] val email: String,
        val companyName:String?,
        val firstName:String?,
        val lastName:String?,
        val phoneNumber:String?,
        val comment:String?,
        @get: [Valid] val address: BrokerAddress
) {
    @ApiModel("CreateBrokerRequest.BrokerAddress")
    data class BrokerAddress(
            @get: [NotBlank] val country: String,
            @get: [NotBlank] val city: String,
            val state: String?,
            val street: String?,
            val number: String?
    )
}