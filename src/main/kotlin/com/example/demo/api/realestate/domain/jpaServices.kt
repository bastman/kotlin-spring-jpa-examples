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

@Component
class JpaPropertyLinksService(
        private val propertyLinksRepository: PropertyLinksRepository
) {
    fun save(@Valid link: PropertyLink): PropertyLink {
        return propertyLinksRepository.save(link)
    }

    fun findById(linkId: UUID): PropertyLink? {
        return propertyLinksRepository
                .getById(linkId)
                .toNullable()
    }

    fun getById(linkId: UUID): PropertyLink {
        return findById(linkId) ?: throw EntityNotFoundException(
                "ENTITY NOT FOUND! query: propertyLink.id=$linkId"
        )
    }

    fun findByFromPropertyIdAndToPropertyId(fromPropertyId: UUID, toPropertyId: UUID): PropertyLink? {
        return propertyLinksRepository
                .getByFromPropertyIdAndToPropertyId(fromPropertyId, toPropertyId)
                .toNullable()
    }

    fun delete(link: PropertyLink) {
        return propertyLinksRepository.delete(link)
    }
}