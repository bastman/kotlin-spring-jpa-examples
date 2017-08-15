package com.example.demo.api.realestate.handler.search

import com.example.demo.api.common.validation.validateRequest
import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.domain.jpa.entities.QueryDslEntity.qProperty
import com.example.demo.querydsl.andAllOf
import com.example.demo.querydsl.andAnyOf
import com.example.demo.querydsl.orderBy
import com.querydsl.jpa.impl.JPAQuery
import org.springframework.stereotype.Component
import org.springframework.validation.Validator
import javax.persistence.EntityManager

@Component
class SearchPropertiesHandler(
        private val validator: Validator,
        private val entityManager: EntityManager
) {
    fun handle(request: SearchPropertiesRequest): SearchPropertiesResponse =
            execute(request = validator.validateRequest(request, "request"))

    private fun execute(request: SearchPropertiesRequest): SearchPropertiesResponse {
        val filters = request.filter.toWhereExpressionDsl()
        val search = request.search.toWhereExpressionDsl()
        val order = request.orderBy.toOrderByExpressionDsl()

        val query = JPAQuery<Property>(entityManager)
        val resultSet = query.from(qProperty)
                .where(
                        qProperty.isNotNull
                                .andAllOf(filters)
                                .andAnyOf(search)
                )
                .orderBy(order)
                .offset(request.offset)
                .limit(request.limit)
                .fetchResults()

        return SearchPropertiesResponse.of(resultSet)
    }
}
