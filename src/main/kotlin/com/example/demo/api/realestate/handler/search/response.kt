package com.example.demo.api.realestate.handler.search

import com.example.demo.api.realestate.domain.Property
import com.example.demo.api.realestate.handler.common.response.ResponsePaging

data class SearchPropertiesResponse(
        val paging: ResponsePaging,
        val properties: List<Property>
)