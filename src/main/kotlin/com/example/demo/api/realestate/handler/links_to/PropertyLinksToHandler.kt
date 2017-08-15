package com.example.demo.api.realestate.handler.links_to

import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyLinksService
import com.example.demo.api.realestate.domain.jpa.services.JpaPropertyService
import com.example.demo.api.realestate.handler.common.response.PropertyDto
import org.springframework.stereotype.Component
import java.util.*

@Component
class PropertyLinksToHandler(
        private val jpaPropertyService: JpaPropertyService,
        private val jpaPropertyLinksService: JpaPropertyLinksService
) {

    fun handle(propertyId: UUID, limit: Long = 1000): PropertyLinksToResponse {
        val property = jpaPropertyService.getById(propertyId)

        val propertyIdList: List<UUID> = jpaPropertyLinksService
                .selectToPropertyIdsWhereFromPropertyIdEquals(
                        fromPropertyId = propertyId,
                        limit = limit
                )

        val properties: List<Property> = jpaPropertyService
                .findByIdList(propertyIdList)

        return PropertyLinksToResponse(
                linksTo = properties.map { it.toDto() },
                property = property.toDto()
        )
    }
}

private fun Property.toDto() = PropertyDto.of(this)