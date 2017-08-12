package com.example.demo.api.realestate.domain

import com.example.demo.api.common.EntityNotFoundException
import com.example.demo.util.optionals.toNullable
import org.springframework.stereotype.Component
import java.util.*
import javax.validation.Valid

@Component
class JpaPropertyService(
        private val propertyRepository: PropertyRepository
) {
    fun save(@Valid property: Property): Property {
        return propertyRepository.save(property)
    }

    fun findById(propertyId: UUID): Property? {
        return propertyRepository
                .getById(propertyId)
                .toNullable()
    }

    fun getById(propertyId: UUID): Property {
        return findById(propertyId) ?: throw EntityNotFoundException(
                "ENTITY NOT FOUND! query: property.id=$propertyId"
        )
    }
}