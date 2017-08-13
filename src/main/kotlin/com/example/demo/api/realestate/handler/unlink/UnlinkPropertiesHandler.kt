package com.example.demo.api.realestate.handler.unlink

import com.example.demo.api.realestate.domain.JpaPropertyLinksService
import com.example.demo.api.realestate.domain.JpaPropertyService
import com.example.demo.api.realestate.domain.Property
import org.springframework.stereotype.Component

@Component
class UnlinkPropertiesHandler(
        private val jpaPropertyService: JpaPropertyService,
        private val jpaPropertyLinksService: JpaPropertyLinksService
) {

    fun handle(request: UnlinkPropertiesRequest): UnlinkPropertiesResponse {
        return execute(request.validated())
    }

    private fun execute(request: UnlinkPropertiesRequest): UnlinkPropertiesResponse {
        val property1 = jpaPropertyService.getById(request.propertyId1)
        val property2 = jpaPropertyService.getById(request.propertyId2)

        unlink(from = property1, to = property2)
        unlink(from = property2, to = property1)

        return UnlinkPropertiesResponse(
                unlinkedPropertyIds = listOf(property1.id, property2.id)
        )
    }

    private fun unlink(from: Property, to: Property) {
        val link = jpaPropertyLinksService.findByFromPropertyIdAndToPropertyId(
                from.id, to.id
        )
        if (link != null) {
            jpaPropertyLinksService.delete(link)
        }
    }
}