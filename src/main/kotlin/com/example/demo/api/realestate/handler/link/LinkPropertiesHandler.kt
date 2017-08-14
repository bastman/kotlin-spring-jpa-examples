package com.example.demo.api.realestate.handler.link

import com.example.demo.api.realestate.domain.JpaPropertyLinksService
import com.example.demo.api.realestate.domain.JpaPropertyService
import com.example.demo.api.realestate.domain.Property
import com.example.demo.api.realestate.domain.PropertyLink
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
        val property1 = jpaPropertyService.getById(request.propertyId1)
        val property2 = jpaPropertyService.getById(request.propertyId2)

        val property1ToProperty2Link = link(from = property1, to = property2)
        val property2ToProperty1Link = link(from = property2, to = property1)

        return LinkPropertiesResponse(
                links = listOf(property1ToProperty2Link, property2ToProperty1Link)
        )
    }

    private fun link(from: Property, to: Property): PropertyLink {

        val link = jpaPropertyLinksService.findByFromPropertyIdAndToPropertyId(
                from.id, to.id
        )

        if (link != null) {
            // already linked
            return link
        }

        val newLink = PropertyLink(
                id = UUID.randomUUID(),
                created = Instant.now(),
                modified = Instant.now(),
                fromPropertyId = from.id,
                toPropertyId = to.id
        )
        return jpaPropertyLinksService.insert(newLink)
    }
}