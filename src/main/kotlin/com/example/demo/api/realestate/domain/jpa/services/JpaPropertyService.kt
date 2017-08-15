package com.example.demo.api.realestate.domain.jpa.services

import com.example.demo.api.common.EntityAlreadyExistException
import com.example.demo.api.common.EntityNotFoundException
import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.domain.jpa.entities.QueryDslEntity
import com.example.demo.api.realestate.domain.jpa.repositories.PropertyRepository
import com.example.demo.util.optionals.toNullable
import com.querydsl.jpa.impl.JPAQuery
import org.springframework.stereotype.Component
import java.util.*
import javax.persistence.EntityManager
import javax.validation.Valid

@Component
class JpaPropertyService(
        private val propertyRepository: PropertyRepository,
        private val entityManager: EntityManager
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

    fun findPropertiesByIdList(
            propertyIdList: List<UUID>
    ): List<Property> {
        val query = JPAQuery<Property>(entityManager)
        val resultSet = query.from(QueryDslEntity.qProperty)
                .where(
                        QueryDslEntity.qProperty.id.`in`(propertyIdList)
                )
                .fetchResults()

        return resultSet.results
    }
}