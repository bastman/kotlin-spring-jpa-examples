package com.example.demo.api.realestate.handler.brokers.crud.search

import com.example.demo.api.realestate.domain.jpa.entities.QueryDslEntity.qBroker
import com.example.demo.api.realestate.handler.common.request.QueryDslRequestParser
import com.fasterxml.jackson.annotation.JsonValue
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import io.swagger.annotations.ApiModel
import java.time.Instant
import java.util.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import com.example.demo.api.realestate.handler.common.request.QueryDslOperation as Op

typealias Filters = List<FieldAndValues>
typealias Sorting = List<SortField>

private object FieldNames {
    const val id = "id"
    const val createdAt = "createdAt"
    const val modifiedAt = "modifiedAt"
    const val email = "email"
    const val address_country = "address.country"
    const val address_city = "address.city"
}

data class SearchBrokersRequest(
        @get:[Min(0)] val offset: Long = 0,
        @get:[Min(1) Max(100)] val limit: Long = 100,
        val filter: Filters? = null,
        val search: Filters? = null,
        val orderBy: Sorting? = null
)

@ApiModel("SearchBrokersRequest.FieldAndValues")
data class FieldAndValues(val field: FilterField, val values: List<String>)

@ApiModel("SearchBrokersRequest.SortableField")
enum class SortField(
        fieldName: String,
        fieldOperation: String,
        val orderSpecifierSupplier: () -> OrderSpecifier<*>
) {
    modifiedAt_asc(FieldNames.modifiedAt, Op.ASC, { qBroker.modified.asc() }),
    modifiedAt_desc(FieldNames.modifiedAt, Op.DESC, { qBroker.modified.desc() }),

    createdAt_asc(FieldNames.createdAt, Op.ASC, { qBroker.created.asc() }),
    createdAt_desc(FieldNames.createdAt, Op.DESC, { qBroker.created.desc() }),

    email_asc(FieldNames.email, Op.ASC, { qBroker.email.asc() }),
    email_desc(FieldNames.email, Op.DESC, { qBroker.email.desc() }),

    ;

    @get:JsonValue val fieldExpression: String = "$fieldName-$fieldOperation"
    override fun toString(): String = fieldExpression
    fun toOrderSpecifier(): OrderSpecifier<*> = orderSpecifierSupplier()
}

@ApiModel("SearchPropertiesRequest.FilterField")
enum class FilterField(
        fieldName: String,
        fieldOperation: String,
        val predicateSupplier: (FilterField, String) -> BooleanExpression
) {
    id_eq(FieldNames.id, Op.LIKE, {
        field, value: String ->
        qBroker.id.eq(value.toUUID(field))
    }),

    modifiedAt_goe(FieldNames.modifiedAt, Op.GOE, {
        field, value: String ->
        qBroker.modified.goe(value.toInstant(field))
    }),
    modifiedAt_loe(FieldNames.modifiedAt, Op.GOE, {
        field, value: String ->
        qBroker.modified.loe(value.toInstant(field))
    }),

    email_like(FieldNames.email, Op.LIKE, {
        _, value: String ->
        qBroker.email.likeIgnoreCase(value)
    }),

    address_country_like(FieldNames.address_country, Op.LIKE, {
        _, value: String ->
        qBroker.address.country.likeIgnoreCase(value)
    }),
    address_city_like(FieldNames.address_city, Op.LIKE, {
        _, value: String ->
        qBroker.address.city.likeIgnoreCase(value)
    }),

    ;

    @get:JsonValue val fieldExpression: String = "$fieldName-$fieldOperation"
    override fun toString(): String = fieldExpression
    fun toBooleanExpression(values: List<String>): List<BooleanExpression> =
            values.map { value -> predicateSupplier(this, value) }.toList()
}

fun Filters?.toWhereExpressionDsl(): List<BooleanExpression> =
        this?.flatMap {
            it.field.toBooleanExpression(it.values)
        } ?: emptyList()

fun Sorting?.toOrderByExpressionDsl(): List<OrderSpecifier<*>> =
        this?.map {
            it.toOrderSpecifier()
        } ?: emptyList()

private fun String.toInstant(field: FilterField): Instant =
        QueryDslRequestParser.asInstant(
                fieldValue = this, fieldExpression = field.fieldExpression
        )

private fun String.toUUID(field: FilterField): UUID =
        QueryDslRequestParser.asUUID(
                fieldValue = this, fieldExpression = field.fieldExpression
        )