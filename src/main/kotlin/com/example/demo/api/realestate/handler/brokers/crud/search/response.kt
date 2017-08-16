package com.example.demo.api.realestate.handler.brokers.crud.search

import com.example.demo.api.realestate.domain.jpa.entities.Broker
import com.example.demo.api.realestate.handler.common.response.BrokerDto
import com.example.demo.api.realestate.handler.common.response.ResponsePaging
import com.querydsl.core.QueryResults

data class SearchBrokersResponse(
        val paging: ResponsePaging,
        val brokers: List<BrokerDto>
) {
    companion object {
        fun of(resultSet: QueryResults<Broker>): SearchBrokersResponse =
                SearchBrokersResponse(
                        paging = ResponsePaging.ofResultSet(resultSet),
                        brokers = resultSet.results.map { it.toDto() }
                )
    }
}

private fun Broker.toDto() = BrokerDto.of(this)