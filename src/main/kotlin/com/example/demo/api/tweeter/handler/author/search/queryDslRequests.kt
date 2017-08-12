package com.example.demo.api.tweeter.handler.author.search

import com.example.demo.jpa.QAuthor
import com.example.demo.querydsl.eq
import com.example.demo.querydsl.gt
import com.example.demo.querydsl.lt
import com.fasterxml.jackson.annotation.JsonValue
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression

data class FieldAndValue(val field: FilterField, val value: String)
data class QueryDslRequest(
        val offset: Long? = 0,
        val limit: Long? = 100,
        val filter: List<FieldAndValue>? = null,
        val search: List<FieldAndValue>? = null,
        val orderBy: List<SortableField>? = null
)

enum class SortableField(
        @get:JsonValue val qname: String,
        val orderSpecifierSupplier: () -> OrderSpecifier<*>
) {
    author_firstName_asc("author.firstName-asc", { QAuthor.author.firstName.asc() }),
    author_firstName_desc("author.firstName-desc", { QAuthor.author.firstName.desc() }),
    author_lastName_asc("author.lastName-asc", { QAuthor.author.lastName.asc() }),
    author_lastName_desc("author.lastName-desc", { QAuthor.author.lastName.desc() }),
    author_email_asc("author.email-asc", { QAuthor.author.email.asc() }),
    author_email_desc("author.email-desc", { QAuthor.author.email.desc() }),

    author_modifiedAt_asc("author.modifiedAt-asc", { QAuthor.author.modifiedAt.asc() }),
    author_modifiedAt_desc("author.modifiedAt-desc", { QAuthor.author.modifiedAt.desc() }),

    ;

    override fun toString(): String {
        return qname
    }

    fun toOrderSpecifier(): OrderSpecifier<*> {
        return orderSpecifierSupplier()
    }
}

enum class FilterField(
        @get:JsonValue val qname: String,
        val predicateSupplier: (String) -> BooleanExpression
) {
    author_firstName_eq("author.firstName-eq", { value: String -> QAuthor.author.firstName.eq(value) }),
    author_firstName_like("author.firstName-like", { value: String -> QAuthor.author.firstName.like(value) }),
    author_lastName_eq("author.lastName-eq", { value: String -> QAuthor.author.lastName.eq(value) }),
    author_lastName_like("author.lastName-like", { value: String -> QAuthor.author.lastName.like(value) }),
    author_email_eq("author.email-eq", { value: String -> QAuthor.author.email.eq(value) }),
    author_email_like("author.email-like", { value: String -> QAuthor.author.email.like(value) }),
    author_version_eq("author.version-eq", { value: String -> QAuthor.author.version.eq(value) }),
    author_version_gt("author.version-gt", { value: String -> QAuthor.author.version.gt(value) }),
    author_version_lt("author.version-lt", { value: String -> QAuthor.author.version.lt(value) }),
    author_modifiedAt_gt("author.modifiedAt-gt", { value: String -> QAuthor.author.modifiedAt.gt(value) }),
    author_modifiedAt_lt("author.modifiedAt-lt", { value: String -> QAuthor.author.modifiedAt.lt(value) })
    ;

    override fun toString(): String {
        return qname
    }

    fun toBooleanExpression(value: String): BooleanExpression {
        return predicateSupplier(value)
    }

}