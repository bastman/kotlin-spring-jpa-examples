package com.example.demo.api.realestate.domain.jpa.entities

import com.example.demo.api.realestate.domain.jpa.entities.QProperty
import com.example.demo.api.realestate.domain.jpa.entities.QPropertyLink

object QueryDslEntity {
    val qProperty = QProperty.property
    val qPropertyLink = QPropertyLink.propertyLink
}