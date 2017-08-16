package com.example.demo.api.realestate.handler.brokers.crud.create

import com.example.demo.api.common.validation.validateRequest
import com.example.demo.api.realestate.domain.jpa.entities.Broker
import com.example.demo.api.realestate.domain.jpa.entities.BrokerAddress
import com.example.demo.api.realestate.domain.jpa.services.JpaBrokerService
import com.example.demo.api.realestate.handler.common.response.BrokerResponse
import org.springframework.stereotype.Component
import org.springframework.validation.Validator
import java.time.Instant
import java.util.*

@Component
class CreateBrokerHandler(
        private val validator: Validator,
        private val jpaBrokerService: JpaBrokerService
) {
    fun handle(request: CreateBrokerRequest): BrokerResponse =
            execute(validator.validateRequest(request, "request"))

    private fun execute(request: CreateBrokerRequest): BrokerResponse {
        val newBroker = Broker(
                id = UUID.randomUUID(),
                created = Instant.now(),
                modified = Instant.now(),
                companyName = request.companyName?.trim() ?: "",
                firstName = request.firstName?.trim() ?: "",
                lastName = request.lastName?.trim() ?: "",
                email = request.email.trim(),
                phoneNumber = request.phoneNumber?.trim() ?: "",
                comment = request.comment?.trim() ?: "",
                address = BrokerAddress(
                        country = request.address.country.trim(),
                        city = request.address.city.trim(),
                        state = request.address.state?.trim() ?: "",
                        street = request.address.street?.trim() ?: "",
                        number = request.address.number?.trim() ?: ""
                )
        )
        return BrokerResponse.of(
                broker = jpaBrokerService.insert(newBroker)
        )
    }
}