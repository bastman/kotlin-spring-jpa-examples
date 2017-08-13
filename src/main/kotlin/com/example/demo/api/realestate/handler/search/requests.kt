package com.example.demo.api.realestate.handler.search

import com.example.demo.api.realestate.domain.PropertyType
import com.example.demo.api.realestate.domain.QueryDslEntity.qProperty
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
    const val name = "name"
    const val type = "type"
    const val address_country = "address.country"
    const val address_city = "address.city"
    const val address_zip = "address.zip"
    const val address_street = "address.street"
    const val address_number = "address.street"
    const val address_district = "address.number"
    const val address_neighborhood = "address.neighborhood"
}

data class SearchPropertiesRequest(
        @get:[Min(0)] val offset: Long = 0,
        @get:[Min(1) Max(100)] val limit: Long = 100,
        val filter: Filters? = null,
        val search: Filters? = null,
        val orderBy: Sorting? = null
)

@ApiModel("SearchPropertiesRequest.FieldAndValues")
data class FieldAndValues(val field: FilterField, val values: List<String>)


@ApiModel("SearchPropertiesRequest.SortableField")
enum class SortField(
        fieldName: String,
        fieldOperation: String,
        val orderSpecifierSupplier: () -> OrderSpecifier<*>
) {
    modifiedAt_asc(FieldNames.modifiedAt, Op.ASC, { qProperty.modified.asc() }),
    modifiedAt_desc(FieldNames.modifiedAt, Op.DESC, { qProperty.modified.desc() }),

    createdAt_asc(FieldNames.createdAt, Op.ASC, { qProperty.created.asc() }),
    createdAt_desc(FieldNames.createdAt, Op.DESC, { qProperty.created.desc() }),

    name_asc(FieldNames.name, Op.ASC, { qProperty.name.asc() }),
    name_desc(FieldNames.name, Op.DESC, { qProperty.name.desc() }),

    type_asc(FieldNames.type, Op.ASC, { qProperty.type.asc() }),
    type_desc(FieldNames.type, Op.DESC, { qProperty.type.desc() }),


    ;

    @get:JsonValue val fieldExpression: String = "$fieldName-$fieldOperation"

    override fun toString(): String {
        return fieldExpression
    }

    fun toOrderSpecifier(): OrderSpecifier<*> {
        return orderSpecifierSupplier()
    }
}

@ApiModel("SearchPropertiesRequest.FilterField")
enum class FilterField(
        fieldName: String,
        fieldOperation: String,
        val predicateSupplier: (FilterField, String) -> BooleanExpression
) {

    modifiedAt_goe(FieldNames.modifiedAt, Op.GOE, {
        field, value: String ->
        qProperty.modified.goe(value.toInstant(field))
    }),
    modifiedAt_loe(FieldNames.modifiedAt, Op.GOE, {
        field, value: String ->
        qProperty.modified.loe(value.toInstant(field))
    }),

    createdAt_goe(FieldNames.createdAt, Op.GOE, {
        field, value: String ->
        qProperty.created.goe(value.toInstant(field))
    }),
    createdAt_loe(FieldNames.createdAt, Op.GOE, {
        field, value: String ->
        qProperty.created.loe(value.toInstant(field))
    }),

    name_like(FieldNames.name, Op.LIKE, {
        _, value: String ->
        qProperty.name.likeIgnoreCase(value)
    }),

    id_eq(FieldNames.id, Op.LIKE, {
        field, value: String ->
        qProperty.id.eq(value.toUUID(field))
    }),

    type_eq(FieldNames.type, Op.LIKE, {
        field, value: String ->
        qProperty.type.eq(value.toPropertyType(field))
    }),

    address_country_like(FieldNames.address_country, Op.LIKE, {
        _, value: String ->
        qProperty.address.country.likeIgnoreCase(value)
    }),
    address_city_like(FieldNames.address_city, Op.LIKE, {
        _, value: String ->
        qProperty.address.city.likeIgnoreCase(value)
    }),
    address_zip_like(FieldNames.address_zip, Op.LIKE, {
        _, value: String ->
        qProperty.address.zip.likeIgnoreCase(value)
    }),
    address_street_like(FieldNames.address_street, Op.LIKE, {
        _, value: String ->
        qProperty.address.street.likeIgnoreCase(value)
    }),
    address_number_like(FieldNames.address_number, Op.LIKE, {
        _, value: String ->
        qProperty.address.number.likeIgnoreCase(value)
    }),
    address_district_like(FieldNames.address_district, Op.LIKE, {
        _, value: String ->
        qProperty.address.district.likeIgnoreCase(value)
    }),
    address_neighborhood_like(FieldNames.address_neighborhood, Op.LIKE, {
        _, value: String ->
        qProperty.address.neighborhood.likeIgnoreCase(value)
    }),
    ;

    @get:JsonValue val fieldExpression: String = "$fieldName-$fieldOperation"

    override fun toString(): String = fieldExpression

    fun toBooleanExpression(values: List<String>): List<BooleanExpression> {
        return values.map { value -> predicateSupplier(this, value) }.toList()
    }
}


fun Filters?.toWhereExpressionDsl(): List<BooleanExpression> {
    return this?.flatMap {
        it.field.toBooleanExpression(it.values)
    } ?: emptyList()
}

fun Sorting?.toOrderByExpressionDsl(): List<OrderSpecifier<*>> {
    return this?.map {
        it.toOrderSpecifier()
    } ?: emptyList()
}

private fun String.toInstant(field: FilterField): Instant {
    return QueryDslRequestParser.asInstant(
            fieldValue = this, fieldExpression = field.fieldExpression
    )
}
private fun String.toUUID(field: FilterField): UUID {
    return QueryDslRequestParser.asUUID(
            fieldValue = this, fieldExpression = field.fieldExpression
    )
}
private fun String.toPropertyType(field: FilterField): PropertyType {
    return QueryDslRequestParser.asPropertyType(
            fieldValue = this, fieldExpression = field.fieldExpression
    )
}