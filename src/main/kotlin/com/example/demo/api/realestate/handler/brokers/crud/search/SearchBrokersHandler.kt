package com.example.demo.api.realestate.handler.brokers.crud.search

import com.example.demo.api.common.validation.validateRequest
import com.example.demo.api.realestate.domain.jpa.entities.Broker
import com.example.demo.api.realestate.domain.jpa.entities.QueryDslEntity.qBroker
import com.example.demo.querydsl.andAllOf
import com.example.demo.querydsl.andAnyOf
import com.example.demo.querydsl.orderBy
import com.querydsl.jpa.impl.JPAQuery
import org.springframework.stereotype.Component
import org.springframework.validation.Validator
import javax.persistence.EntityManager

@Component
class SearchBrokersHandler(
        private val validator: Validator,
        private val entityManager: EntityManager
) {
    fun handle(request: SearchBrokersRequest): SearchBrokersResponse =
            execute(request = validator.validateRequest(request, "request"))

    private fun execute(request: SearchBrokersRequest): SearchBrokersResponse {
        val filters = request.filter.toWhereExpressionDsl()
        val search = request.search.toWhereExpressionDsl()
        val order = request.orderBy.toOrderByExpressionDsl()

        val query = JPAQuery<Broker>(entityManager)
        val resultSet = query.from(qBroker)
                .where(
                        qBroker.isNotNull
                                .andAllOf(filters)
                                .andAnyOf(search)
                )
                .orderBy(order)
                .offset(request.offset)
                .limit(request.limit)
                .fetchResults()

        return SearchBrokersResponse.of(resultSet)
    }
}