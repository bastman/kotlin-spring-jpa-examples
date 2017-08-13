package com.example.demo.api.realestate.handler.search

import com.example.demo.api.realestate.domain.QueryDslEntity.qProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import io.swagger.annotations.ApiModel
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import com.example.demo.api.realestate.handler.common.request.QueryDslOperation as Op

typealias Filters = List<FieldAndValues>
typealias Sorting = List<SortField>

data class SearchPropertiesRequest(
        @get:[Min(0)] val offset: Long = 0,
        @get:[Min(1) Max(100)] val limit: Long = 100,
        val filter: Filters? = null,
        val search: Filters? = null,
        val orderBy: Sorting? = null
)

private object FieldName {
    const val id = "id"
    const val name = "name"
    const val type = "type"
    const val address_country = "address.country"
    const val address_city = "address.city"
}

@ApiModel("SearchPropertiesRequest.FieldAndValues")
data class FieldAndValues(val field: FilterField, val values: List<String>)


@ApiModel("SearchPropertiesRequest.SortableField")
enum class SortField(
        fieldName: String,
        fieldOperation: String,
        val orderSpecifierSupplier: () -> OrderSpecifier<*>
) {

    name_asc(FieldName.name, Op.ASC, { qProperty.name.asc() }),
    name_desc(FieldName.name, Op.DESC, { qProperty.name.desc() }),


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
    name_like(FieldName.name, Op.LIKE, {
        _, value: String ->
        qProperty.name.like(value)
    }),

    ;

    @get:JsonValue val fieldExpression: String = "$fieldName-$fieldOperation"

    override fun toString(): String {
        return fieldExpression
    }

    fun toBooleanExpression(value: String): BooleanExpression {
        return predicateSupplier(this, value)
    }

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