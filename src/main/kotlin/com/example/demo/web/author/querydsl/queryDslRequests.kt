package com.example.demo.web.author.querydsl

import com.example.demo.jpa.QAuthor
import com.example.demo.querydsl.eq
import com.example.demo.querydsl.gt
import com.example.demo.querydsl.lt
import com.fasterxml.jackson.annotation.JsonValue
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression


data class FieldAndValue(val field: FilterField, val value: String)
data class QueryDslRequest(
        val filter: List<FieldAndValue>? = null,
        val search: List<FieldAndValue>? = null,
        val orderBy: List<SortableField>
)

enum class SortableField(
        @get:JsonValue val qname: String
) {
    AUTHOR_FIRST_NAME_ASC("author.firstName-ASC"),
    AUTHOR_FIRST_NAME_DESC("author.firstName-DESC"),
    AUTHOR_LAST_NAME_ASC("author.lastName-ASC"),
    AUTHOR_LAST_NAME_DESC("author.lastName-DESC"),
    AUTHOR_EMAIL_ASC("author.email-ASC"),
    AUTHOR_EMAIL_DESC("author.email-DESC"),

    authorModifiedAt_ASC("author.modifiedAt-ASC"),
    authorModifiedAt_DESC("author.modifiedAt-DESC")

    ;

    override fun toString(): String {
        return qname
    }
}

fun SortableField.toQueryDsl(): OrderSpecifier<*> {
    return when (this) {
        SortableField.AUTHOR_FIRST_NAME_ASC -> QAuthor.author.firstName.asc()
        SortableField.AUTHOR_FIRST_NAME_DESC -> QAuthor.author.firstName.desc()
        SortableField.AUTHOR_LAST_NAME_ASC -> QAuthor.author.lastName.asc()
        SortableField.AUTHOR_LAST_NAME_DESC -> QAuthor.author.lastName.desc()
        SortableField.AUTHOR_EMAIL_ASC -> QAuthor.author.email.asc()
        SortableField.AUTHOR_EMAIL_DESC -> QAuthor.author.email.desc()
        SortableField.authorModifiedAt_ASC -> QAuthor.author.modifiedAt.asc()
        SortableField.authorModifiedAt_DESC -> QAuthor.author.modifiedAt.desc()
    }
}

enum class FilterField(
        @get:JsonValue val qname: String
) {
    author_firstName_eq("author.firstName-eq"),
    author_firstName_like("author.firstName-like"),
    author_lastName_eq("author.lastName-eq"),
    author_lastName_like("author.lastName-like"),
    author_email_eq("author.email-eq"),
    author_email_like("author.email-like"),
    author_version_eq("author.version-eq"),
    author_version_gt("author.version-gt"),
    author_version_lt("author.version-lt"),
    author_modifiedAt_gt("author.modifiedAt-gt"),
    author_modifiedAt_lt("author.modifiedAt-lt")
    ;

    override fun toString(): String {
        return qname
    }

}

fun FilterField.toQueryDsl(value: String): BooleanExpression {

    val field = this
    val author = QAuthor.author
    return when (field) {
        FilterField.author_firstName_eq -> author.firstName.eq(value)
        FilterField.author_firstName_like -> author.firstName.like(value)
        FilterField.author_lastName_eq -> author.lastName.eq(value)
        FilterField.author_lastName_like -> author.lastName.like(value)
        FilterField.author_email_eq -> author.email.eq(value)
        FilterField.author_email_like -> author.email.like(value)
        FilterField.author_version_eq -> author.version.eq(value)
        FilterField.author_version_gt -> author.version.gt(value)
        FilterField.author_modifiedAt_gt -> author.modifiedAt.gt(value)
        FilterField.author_modifiedAt_lt -> author.modifiedAt.lt(value)

        else -> throw RuntimeException("BadRequest! FILTER field=$field")
    }
}