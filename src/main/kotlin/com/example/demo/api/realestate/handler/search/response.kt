package com.example.demo.api.realestate.handler.search

import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.handler.common.response.PropertyDto
import com.example.demo.api.realestate.handler.common.response.ResponsePaging
import com.querydsl.core.QueryResults

data class SearchPropertiesResponse(
        val paging: ResponsePaging,
        val properties: List<PropertyDto>
) {
    companion object {
        fun of(resultSet: QueryResults<Property>): SearchPropertiesResponse =
                SearchPropertiesResponse(
                        paging = ResponsePaging.ofResultSet(resultSet),
                        properties = resultSet.results.map { it.toDto() }
                )
    }
}

private fun Property.toDto() = PropertyDto.of(this)