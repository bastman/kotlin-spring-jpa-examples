package com.example.demo.api.realestate.handler.update

import com.example.demo.api.common.validation.validateBean
import com.example.demo.api.realestate.domain.JpaPropertyService
import com.example.demo.util.fp.pipe
import org.springframework.stereotype.Component
import org.springframework.validation.Validator
import java.util.*

@Component
class UpdatePropertyHandler(
        private val validator: Validator,
        private val jpaPropertyService: JpaPropertyService
) {

    fun handle(propertyId: UUID, request: UpdatePropertyRequest): Any? {
        return execute(
                propertyId = propertyId,
                request = request.validateBean(validator, "request")
        )
    }

    private fun execute(
            propertyId: UUID,
            request: UpdatePropertyRequest
    ): Any? {
        val property = jpaPropertyService.getById(propertyId)
                .pipe {
                    if (request.type != null) {
                        it.copy(type = request.type)
                    } else it
                }
                .pipe {
                    if (request.name != null) {
                        it.copy(name = request.name)
                    } else it
                }

        return jpaPropertyService.save(property)
    }
}