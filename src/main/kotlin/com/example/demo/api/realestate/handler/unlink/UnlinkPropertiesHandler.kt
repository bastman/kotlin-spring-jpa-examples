package com.example.demo.api.realestate.handler.unlink

import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyLinksService
import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyService
import com.example.demo.api.realestate.domain.jpa.entities.QueryDslEntity
import com.example.demo.querydsl.and
import com.querydsl.core.QueryResults
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class UnlinkPropertiesHandler(
        private val jpaPropertyService: JpaPropertyService,
        private val jpaPropertyLinksService: JpaPropertyLinksService,
        private val queryFactory: JPAQueryFactory
) {

    fun handle(request: UnlinkPropertiesRequest): UnlinkPropertiesResponse {
        return execute(request.validated())
    }

    private fun execute(request: UnlinkPropertiesRequest): UnlinkPropertiesResponse {
        val propertyId: UUID = jpaPropertyService.requireExists(request.fromPropertyId)
        val toPropertyId: UUID = jpaPropertyService.requireExists(request.toPropertyId)

        unlinkFrom(fromPropertyId = propertyId, toPropertyId = toPropertyId)

        jpaPropertyLinksService.findByFromPropertyIdAndToPropertyId(
                fromPropertyId = propertyId, toPropertyId = toPropertyId
        )

        val linkedToPropertyIds = loadLinkedToPropertyIds(
                fromPropertyId = propertyId, offset = 0, limit = 1000
        )

        val linkedByPropertyIds = loadLinkedByPropertyIds(
                toPropertyId = propertyId, offset = 0, limit = 1000
        )

        return UnlinkPropertiesResponse(
                linkedToPropertyIds = linkedToPropertyIds,
                linkedByPropertyIds = linkedByPropertyIds
        )
    }

    private fun unlinkFrom(fromPropertyId: UUID, toPropertyId: UUID) {
        val link = jpaPropertyLinksService.findByFromPropertyIdAndToPropertyId(
                fromPropertyId, toPropertyId
        )
        if (link != null) {
            jpaPropertyLinksService.delete(link)
        }
    }

    private fun loadLinkedToPropertyIds(
            fromPropertyId: UUID, offset: Long, limit: Long
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

    private fun loadLinkedByPropertyIds(
            toPropertyId: UUID, offset: Long, limit: Long
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
}