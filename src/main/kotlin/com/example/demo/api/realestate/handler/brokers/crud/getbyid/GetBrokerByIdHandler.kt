package com.example.demo.api.realestate.handler.brokers.crud.getbyid

import com.example.demo.api.realestate.domain.jpa.services.JpaBrokerService
import com.example.demo.api.realestate.handler.common.response.BrokerResponse
import org.springframework.stereotype.Component
import java.util.*

@Component
class GetBrokerByIdHandler(
        private val jpaBrokerService: JpaBrokerService
) {
    fun handle(brokerId: UUID): BrokerResponse =
            BrokerResponse.of(broker = jpaBrokerService.getById(brokerId))
}