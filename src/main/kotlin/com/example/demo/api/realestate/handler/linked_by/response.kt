package com.example.demo.api.realestate.handler.linked_by

import com.example.demo.api.realestate.handler.common.response.PropertyDto

data class PropertyLinkedByResponse(
        val linkedBy: List<PropertyDto>,
        val property: PropertyDto
)