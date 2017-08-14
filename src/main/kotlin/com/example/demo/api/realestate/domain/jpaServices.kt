package com.example.demo.api.realestate.domain

import com.example.demo.api.common.EntityAlreadyExistException
import com.example.demo.api.common.EntityNotFoundException
import com.example.demo.util.optionals.toNullable
import org.springframework.stereotype.Component
import java.util.*
import javax.validation.Valid

@Component
class JpaPropertyService(
        private val propertyRepository: PropertyRepository
) {
    fun exists(propertyId: UUID): Boolean = propertyRepository.exists(propertyId)

    fun requireExists(propertyId: UUID): UUID {
        return if (exists(propertyId)) {
            propertyId
        } else throw EntityNotFoundException(
                "ENTITY NOT FOUND! query: property.id=$propertyId"
        )
    }

    fun requireDoesNotExist(propertyId: UUID): UUID {
        return if (!exists(propertyId)) {
            propertyId
        } else throw EntityAlreadyExistException(
                "ENTITY ALREADY EXIST! query: property.id=$propertyId"
        )
    }

    fun insert(@Valid property: Property): Property {
        requireDoesNotExist(property.id)

        return propertyRepository.save(property)
    }

    fun update(@Valid property: Property): Property {
        requireExists(property.id)

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

    fun exists(propertyLinkId: UUID): Boolean = propertyLinksRepository.exists(propertyLinkId)

    fun requireExists(propertyLinkId: UUID): UUID {
        return if (exists(propertyLinkId)) {
            propertyLinkId
        } else throw EntityNotFoundException(
                "ENTITY NOT FOUND! query: PropertyLink.id=$propertyLinkId"
        )
    }

    fun requireDoesNotExist(propertyLinkId: UUID): UUID {
        return if (!exists(propertyLinkId)) {
            propertyLinkId
        } else throw EntityAlreadyExistException(
                "ENTITY ALREADY EXIST! query: PropertyLink.id=$propertyLinkId"
        )
    }

    fun insert(@Valid link: PropertyLink): PropertyLink {
        requireDoesNotExist(link.id)

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