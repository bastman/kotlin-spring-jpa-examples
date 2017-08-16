package com.example.demo.api.realestate.handler.brokers.crud.update

import com.example.demo.api.common.validation.validateRequest
import com.example.demo.api.realestate.domain.jpa.entities.Broker
import com.example.demo.api.realestate.domain.jpa.entities.BrokerAddress
import com.example.demo.api.realestate.domain.jpa.services.JpaBrokerService
import com.example.demo.api.realestate.handler.common.response.BrokerResponse
import com.example.demo.util.fp.pipe
import org.springframework.stereotype.Component
import org.springframework.validation.Validator
import java.util.*

@Component
class UpdateBrokerHandler(
        private val validator: Validator,
        private val jpaBrokerService: JpaBrokerService
) {
    fun handle(brokerId: UUID, request: UpdateBrokerRequest): BrokerResponse =
            execute(
                    brokerId = brokerId,
                    request = validator.validateRequest(request, "request")
            )

    private fun execute(brokerId: UUID, request: UpdateBrokerRequest): BrokerResponse {
        val property = jpaBrokerService.getById(brokerId)
                .copyWithUpdateRequest(request)

        return BrokerResponse.of(
                broker = jpaBrokerService.update(property)
        )
    }
}

private fun Broker.copyWithUpdateRequest(req: UpdateBrokerRequest): Broker =
        this.pipe {
            if (req.email != null) it.copy(email = req.email) else it
        }.pipe {
            if (req.companyName != null) it.copy(companyName = req.companyName.trim()) else it
        }.pipe {
            if (req.comment != null) it.copy(comment = req.comment.trim()) else it
        }.pipe {
            if (req.firstName != null) it.copy(firstName = req.firstName.trim()) else it
        }.pipe {
            if (req.lastName != null) it.copy(firstName = req.lastName.trim()) else it
        }.pipe {
            if (req.phoneNumber != null) it.copy(phoneNumber = req.phoneNumber.trim()) else it
        }.pipe {
            if (req.address != null) {
                it.copy(address = it.address.copyWithUpdateRequest(req.address))
            } else {
                it
            }
        }

private fun BrokerAddress.copyWithUpdateRequest(
        req: UpdateBrokerRequest.UpdateBrokerAddress
): BrokerAddress =
        this.pipe {
            if (req.country != null) it.copy(country = req.country.trim()) else it
        }.pipe {
            if (req.city != null) it.copy(city = req.city.trim()) else it
        }.pipe {
            if (req.state != null) it.copy(state = req.state.trim()) else it
        }.pipe {
            if (req.street != null) it.copy(street = req.street.trim()) else it
        }.pipe {
            if (req.number != null) it.copy(number = req.number.trim()) else it
        }
