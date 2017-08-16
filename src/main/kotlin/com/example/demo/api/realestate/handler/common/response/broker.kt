package com.example.demo.api.realestate.handler.common.response

import com.example.demo.api.realestate.domain.jpa.entities.Broker
import com.example.demo.api.realestate.domain.jpa.entities.BrokerAddress
import java.time.Instant
import java.util.*

data class BrokerResponse(val broker: BrokerDto) {
    companion object {
        fun of(broker: Broker): BrokerResponse = BrokerResponse(broker = broker.toDto())
    }
}

data class BrokerDto(
        val id: UUID,
        val version: Int,
        val createdAt: Instant,
        val modified: Instant,

        val companyName: String,
        val firstName: String,
        val lastName: String,
        val email: String,
        val phoneNumber: String,
        val comment: String,

        val address: BrokerAddressDto
) {
    companion object {
        fun of(source: Broker): BrokerDto =
                BrokerDto(
                        id = source.id,
                        version = source.version,
                        createdAt = source.getCreatedAt(),
                        modified = source.getModifiedAt(),
                        companyName = source.companyName,
                        email = source.email,
                        phoneNumber = source.phoneNumber,
                        firstName = source.firstName,
                        lastName = source.lastName,
                        comment = source.comment,
                        address = BrokerAddressDto.of(source.address)
                )
    }
}

data class BrokerAddressDto(
        val country: String,
        val city: String,
        val state: String,
        val street: String,
        val number: String
) {
    companion object {
        fun of(source: BrokerAddress): BrokerAddressDto =
                BrokerAddressDto(
                        country = source.country,
                        city = source.city,
                        state = source.state,
                        street = source.street,
                        number = source.number
                )
    }
}

private fun Broker.toDto() = BrokerDto.of(this)