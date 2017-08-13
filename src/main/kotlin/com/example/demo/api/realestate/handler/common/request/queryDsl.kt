package com.example.demo.api.realestate.handler.common.request

import com.example.demo.api.common.BadRequestException
import com.example.demo.api.realestate.domain.PropertyType
import java.time.Instant
import java.util.*

object QueryDslOperation {
    const val ASC = "asc"
    const val DESC = "desc"
    const val LIKE = "like"
    const val EQ = "eq"
    const val GOE = "goe"
    const val LOE = "loe"
}

object QueryDslRequestParser {
    fun asInstant(fieldValue:String, fieldExpression: String): Instant {
        return try {
            Instant.parse(fieldValue)
        } catch (all: Exception) {
            throw BadRequestException(
                    "Failed to parse field.value"
                            + " provided by field.expression=$fieldExpression"
                            +" as Instant!"
                            +" reason=${all.message} !"
                            +" example=$INSTANT_EXAMPLE"
            )
        }
    }

    fun asUUID(fieldValue:String, fieldExpression: String):UUID {
        return try {
            UUID.fromString(fieldValue)
        } catch (all: Exception) {
            throw BadRequestException(
                    "Failed to parse field.value"
                            + " provided by field.expression=$fieldExpression"
                            +" as UUID!"
                            +" reason=${all.message} !"
                            +" example=$UUID_EXAMPLE"
            )
        }
    }

    fun asPropertyType(fieldValue:String, fieldExpression: String):PropertyType {
        return try {
            PropertyType.valueOf(fieldValue)
        } catch (all: Exception) {
            throw BadRequestException(
                    "Failed to parse field.value"
                            + " provided by field.expression=$fieldExpression"
                            +" as PropertyType!"
                            +" reason=${all.message} !"
                            +" examples=$PROPERTY_TYPES_ALLOWED"
            )
        }
    }

    private val INSTANT_EXAMPLE = Instant.ofEpochSecond(1501595115)
    private val UUID_EXAMPLE = UUID.randomUUID()
    private val PROPERTY_TYPES_ALLOWED = PropertyType.values()
}
