package com.example.demo.api.realestate.routing

import com.example.demo.api.realestate.handler.brokers.crud.create.CreateBrokerHandler
import com.example.demo.api.realestate.handler.brokers.crud.create.CreateBrokerRequest
import com.example.demo.api.realestate.handler.brokers.crud.getbyid.GetBrokerByIdHandler
import com.example.demo.api.realestate.handler.common.response.BrokerResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = arrayOf("*"))
class BrokersCrudController(
        private val getByIdHandler: GetBrokerByIdHandler,
        private val createHandler: CreateBrokerHandler
) {
    @GetMapping("/brokers/{brokerId}")
    fun getById(@PathVariable brokerId: UUID): BrokerResponse =
            getByIdHandler.handle(brokerId)

    @PostMapping("/brokers")
    fun create(@RequestBody request: CreateBrokerRequest): BrokerResponse =
            createHandler.handle(request)
}