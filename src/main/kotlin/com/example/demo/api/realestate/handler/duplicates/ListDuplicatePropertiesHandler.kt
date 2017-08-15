package com.example.demo.api.realestate.handler.duplicates

import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.domain.jpa.entities.QueryDslEntity.qProperty
import com.example.demo.api.realestate.domain.jpa.entities.QueryDslEntity.qPropertyLink
import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyService
import com.example.demo.querydsl.and
import com.querydsl.core.QueryResults
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.*
import javax.persistence.EntityManager

@Component
class ListDuplicatePropertiesHandler(
        private val jpaPropertyService: JpaPropertyService,
        private val queryFactory: JPAQueryFactory,
        private val entityManager: EntityManager
) {

    fun handle(propertyId: UUID): ListDuplicatePropertiesResponse {
        val property = jpaPropertyService.getById(propertyId)
        val limit = 1000L
        val offset = 0L

        val linkedFromPropertyIdList: List<UUID> = loadLinksFromProperty(
                propertyId = propertyId, limit = limit, offset = offset
        )
        val linkedToPropertyIdList: List<UUID> = loadLinksToProperty(
                propertyId = propertyId, limit = limit, offset = offset
        )

        val mergedPropertyIdList = (linkedFromPropertyIdList + linkedToPropertyIdList)
                .distinct()

        val duplicateProperties: List<Property> = findPropertyIdsByIdList(mergedPropertyIdList)

        return ListDuplicatePropertiesResponse(
                linksTo = linkedToPropertyIdList,
                linksFrom = linkedFromPropertyIdList,
                duplicateProperties = duplicateProperties,
                property = property
        )
    }

    private fun loadLinksFromProperty(
            propertyId: UUID, offset: Long, limit: Long
    ): List<UUID> {
        val resultSet: QueryResults<UUID> = queryFactory
                .select(qPropertyLink.toPropertyId)
                .from(qPropertyLink)
                .where(
                        qPropertyLink.fromPropertyId.eq(propertyId) and
                                qPropertyLink.toPropertyId.ne(propertyId)
                )
                .orderBy(qPropertyLink.modified.desc())
                .offset(offset)
                .limit(limit)
                .fetchResults()

        return resultSet.results
    }

    private fun loadLinksToProperty(
            propertyId: UUID, offset: Long, limit: Long
    ): List<UUID> {
        val resultSet: QueryResults<UUID> = queryFactory
                .select(qPropertyLink.fromPropertyId)
                .from(qPropertyLink)
                .where(
                        qPropertyLink.toPropertyId.eq(propertyId) and
                                qPropertyLink.fromPropertyId.ne(propertyId)
                )
                .orderBy(qPropertyLink.modified.desc())
                .offset(offset)
                .limit(limit)
                .fetchResults()

        return resultSet.results
    }

    private fun findPropertyIdsByIdList(
            propertyIdList: List<UUID>
    ): List<Property> {
        val query = JPAQuery<Property>(entityManager)
        val resultSet = query.from(qProperty)
                .where(
                        qProperty.id.`in`(propertyIdList)
                )
                .fetchResults()

        return resultSet.results
    }
}

data class LinkIdAndPropertyId(
        val linkId: UUID,
        val propertyId: UUID
)