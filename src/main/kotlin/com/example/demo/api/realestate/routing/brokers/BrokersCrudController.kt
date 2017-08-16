package com.example.demo.api.realestate.routing.brokers

import com.example.demo.api.realestate.handler.brokers.crud.create.CreateBrokerHandler
import com.example.demo.api.realestate.handler.brokers.crud.create.CreateBrokerRequest
import com.example.demo.api.realestate.handler.brokers.crud.getbyid.GetBrokerByIdHandler
import com.example.demo.api.realestate.handler.brokers.crud.search.SearchBrokersHandler
import com.example.demo.api.realestate.handler.brokers.crud.search.SearchBrokersRequest
import com.example.demo.api.realestate.handler.brokers.crud.search.SearchBrokersResponse
import com.example.demo.api.realestate.handler.brokers.crud.update.UpdateBrokerHandler
import com.example.demo.api.realestate.handler.brokers.crud.update.UpdateBrokerRequest
import com.example.demo.api.realestate.handler.common.response.BrokerResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = arrayOf("*"))
class BrokersCrudController(
        private val getByIdHandler: GetBrokerByIdHandler,
        private val createHandler: CreateBrokerHandler,
        private val updateHandler: UpdateBrokerHandler,
        private val searchHandler: SearchBrokersHandler
) {
    @GetMapping("/brokers/{brokerId}")
    fun getById(@PathVariable brokerId: UUID): BrokerResponse =
            getByIdHandler.handle(brokerId)

    @PostMapping("/brokers")
    fun create(@RequestBody request: CreateBrokerRequest): BrokerResponse =
            createHandler.handle(request)

    @PostMapping("/brokers/{brokerId}")
    fun update(@PathVariable brokerId: UUID, @RequestBody request: UpdateBrokerRequest): BrokerResponse =
            updateHandler.handle(brokerId, request)

    @PostMapping("/brokers/search")
    fun search(@RequestBody request: SearchBrokersRequest): SearchBrokersResponse =
            searchHandler.handle(request)
}