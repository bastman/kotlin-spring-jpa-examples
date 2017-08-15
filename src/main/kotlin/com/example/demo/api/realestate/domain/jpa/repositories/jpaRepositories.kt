package com.example.demo.api.realestate.domain.jpa.repositories

import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.domain.jpa.entities.PropertyLink
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PropertyRepository : JpaRepository<Property, UUID> {
    fun getById(id: UUID): Optional<Property>
}

@Repository
interface PropertyLinksRepository : JpaRepository<PropertyLink, UUID> {
    fun getById(id: UUID): Optional<PropertyLink>
    fun getByFromPropertyIdAndToPropertyId(fromPropertyId: UUID, toPropertyId: UUID): Optional<PropertyLink>
}
