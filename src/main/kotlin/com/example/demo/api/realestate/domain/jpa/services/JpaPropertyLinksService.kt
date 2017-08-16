package com.example.demo.api.realestate.domain.jpa.services

import com.example.demo.api.common.EntityAlreadyExistException
import com.example.demo.api.common.EntityNotFoundException
import com.example.demo.api.realestate.domain.jpa.entities.PropertyLink
import com.example.demo.api.realestate.domain.jpa.entities.QueryDslEntity.qPropertyLink
import com.example.demo.api.realestate.domain.jpa.repositories.PropertyLinksRepository
import com.example.demo.querydsl.and
import com.example.demo.util.optionals.toNullable
import com.querydsl.core.QueryResults
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.*
import javax.validation.Valid


@Component
class JpaPropertyLinksService(
        private val propertyLinksRepository: PropertyLinksRepository,
        private val queryFactory: JPAQueryFactory
) {

    fun exists(propertyLinkId: UUID): Boolean = propertyLinksRepository.exists(propertyLinkId)

    fun findById(linkId: UUID): PropertyLink? =
            propertyLinksRepository
                    .getById(linkId)
                    .toNullable()

    fun getById(linkId: UUID): PropertyLink =
            findById(linkId) ?: throw EntityNotFoundException(
                    "ENTITY NOT FOUND! query: propertyLink.id=$linkId"
            )

    fun requireExists(propertyLinkId: UUID): UUID =
            if (exists(propertyLinkId)) {
                propertyLinkId
            } else throw EntityNotFoundException(
                    "ENTITY NOT FOUND! query: PropertyLink.id=$propertyLinkId"
            )

    fun requireDoesNotExist(propertyLinkId: UUID): UUID =
            if (!exists(propertyLinkId)) {
                propertyLinkId
            } else throw EntityAlreadyExistException(
                    "ENTITY ALREADY EXIST! query: PropertyLink.id=$propertyLinkId"
            )

    fun insert(@Valid link: PropertyLink): PropertyLink {
        requireDoesNotExist(link.id)
        return propertyLinksRepository.save(link)
    }

    fun delete(link: PropertyLink) = propertyLinksRepository.delete(link)

    fun findByFromPropertyIdAndToPropertyId(fromPropertyId: UUID, toPropertyId: UUID): PropertyLink? =
            propertyLinksRepository
                    .getByFromPropertyIdAndToPropertyId(fromPropertyId, toPropertyId)
                    .toNullable()

    fun selectFromPropertyIdsWhereToPropertyIdEquals(
            toPropertyId: UUID, offset: Long = 0, limit: Long
    ): List<UUID> {
        val resultSet: QueryResults<UUID> = queryFactory
                .select(qPropertyLink.fromPropertyId)
                .from(qPropertyLink)
                .where(
                        qPropertyLink.toPropertyId.eq(toPropertyId) and
                                qPropertyLink.fromPropertyId.ne(toPropertyId)
                )
                .orderBy(qPropertyLink.modified.desc())
                .offset(offset)
                .limit(limit)
                .fetchResults()

        return resultSet.results
    }

    fun selectToPropertyIdsWhereFromPropertyIdEquals(
            fromPropertyId: UUID, offset: Long = 0, limit: Long
    ): List<UUID> {
        val resultSet: QueryResults<UUID> = queryFactory
                .select(qPropertyLink.toPropertyId)
                .from(qPropertyLink)
                .where(
                        qPropertyLink.fromPropertyId.eq(fromPropertyId) and
                                qPropertyLink.toPropertyId.ne(fromPropertyId)
                )
                .orderBy(qPropertyLink.modified.desc())
                .offset(offset)
                .limit(limit)
                .fetchResults()

        return resultSet.results
    }
}