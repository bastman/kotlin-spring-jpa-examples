package com.example.demo.api.realestate.domain.jpa.services

import com.example.demo.api.common.EntityAlreadyExistException
import com.example.demo.api.common.EntityNotFoundException
import com.example.demo.api.realestate.domain.jpa.entities.QueryDslEntity
import com.example.demo.api.realestate.domain.jpa.entities.PropertyLink
import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.domain.jpa.repositories.PropertyLinksRepository
import com.example.demo.api.realestate.domain.jpa.repositories.PropertyRepository
import com.example.demo.querydsl.and
import com.example.demo.util.optionals.toNullable
import com.querydsl.core.QueryResults
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
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

@Component
class JpaPropertyLinksService(
        private val propertyLinksRepository: PropertyLinksRepository,
        private val queryFactory: JPAQueryFactory
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

    fun selectFromPropertyIdsWhereToPropertyIdEquals(
            toPropertyId: UUID, offset: Long = 0, limit: Long
    ): List<UUID> {
        val resultSet: QueryResults<UUID> = queryFactory
                .select(QueryDslEntity.qPropertyLink.fromPropertyId)
                .from(QueryDslEntity.qPropertyLink)
                .where(
                        QueryDslEntity.qPropertyLink.toPropertyId.eq(toPropertyId) and
                                QueryDslEntity.qPropertyLink.fromPropertyId.ne(toPropertyId)
                )
                .orderBy(QueryDslEntity.qPropertyLink.modified.desc())
                .offset(offset)
                .limit(limit)
                .fetchResults()

        return resultSet.results
    }

    fun selectToPropertyIdsWhereFromPropertyIdEquals(
            fromPropertyId: UUID, offset: Long = 0, limit: Long
    ): List<UUID> {
        val resultSet: QueryResults<UUID> = queryFactory
                .select(QueryDslEntity.qPropertyLink.toPropertyId)
                .from(QueryDslEntity.qPropertyLink)
                .where(
                        QueryDslEntity.qPropertyLink.fromPropertyId.eq(fromPropertyId) and
                                QueryDslEntity.qPropertyLink.toPropertyId.ne(fromPropertyId)
                )
                .orderBy(QueryDslEntity.qPropertyLink.modified.desc())
                .offset(offset)
                .limit(limit)
                .fetchResults()

        return resultSet.results
    }

}