package com.example.demo.web.common

import org.springframework.data.domain.Page

data class Pagination(
        val page: Int,
        val pageSize: Int,
        val totalPages: Int,
        val totalItems: Long
) {
    companion object {
        fun ofPageResult(pageResult: Page<*>): Pagination {
            return Pagination(
                    pageSize = pageResult.size,
                    totalPages = pageResult.totalPages,
                    page = pageResult.number,
                    totalItems = pageResult.totalElements
            )
        }
    }
}