package com.example.demo.api.realestate.handler.link

import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyLinksService
import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyService
import com.example.demo.api.realestate.domain.jpa.entities.PropertyLink
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class LinkPropertiesHandler(
        private val jpaPropertyService: JpaPropertyService,
        private val jpaPropertyLinksService: JpaPropertyLinksService
) {

    fun handle(request: LinkPropertiesRequest): LinkPropertiesResponse {
        return execute(request.validated())
    }

    private fun execute(request: LinkPropertiesRequest): LinkPropertiesResponse {
        val propertyId1: UUID = jpaPropertyService.requireExists(request.propertyId1)
        val propertyId2: UUID = jpaPropertyService.requireExists(request.propertyId2)

        val links = markAsDuplicates(propertyId1 = propertyId1, propertyId2 = propertyId2)

        // child relations?
        return LinkPropertiesResponse(
                links = links
        )
    }

    private fun markAsDuplicates(
            propertyId1: UUID, propertyId2: UUID
    ): List<PropertyLink> {
        val property1ToProperty2Link = link(fromPropertyId = propertyId1, toPropertyId = propertyId2)
        val property2ToProperty1Link = link(fromPropertyId = propertyId2, toPropertyId = propertyId1)
        return listOf(property1ToProperty2Link, property2ToProperty1Link)
    }

    private fun link(fromPropertyId: UUID, toPropertyId: UUID): PropertyLink {
        val link = jpaPropertyLinksService.findByFromPropertyIdAndToPropertyId(
                fromPropertyId = fromPropertyId, toPropertyId = toPropertyId
        )

        if (link != null) {
            // already linked
            return link
        }

        val newLink = PropertyLink(
                id = UUID.randomUUID(),
                created = Instant.now(),
                modified = Instant.now(),
                fromPropertyId = fromPropertyId,
                toPropertyId = toPropertyId
        )

        return jpaPropertyLinksService.insert(newLink)
    }
}