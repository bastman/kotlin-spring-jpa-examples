package com.example.demo.api.realestate.handler.common.response

import com.querydsl.core.QueryResults
import io.swagger.annotations.ApiModel

@ApiModel("QueryDslResponsePageMeta")
data class ResponsePaging(
        val offset: Long,
        val limit: Long,
        val total: Long
) {
    companion object {
        fun ofResultSet(resultSet: QueryResults<*>): ResponsePaging {
            return ResponsePaging(
                    offset = resultSet.offset,
                    limit = resultSet.limit,
                    total = resultSet.total
            )
        }
    }
}