package com.example.demo.web.author.querydsl

import com.example.demo.jpa.QAuthor
import com.fasterxml.jackson.annotation.JsonValue
import com.querydsl.core.types.CollectionExpression
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.StringPath


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
    //,
    //authorLastName_eqAll("author.lastName-eqAll")
    ;

    /*

    "firstName-eq" -> author.firstName.eq(value)
                "firstName-like" -> author.firstName.like(value)
                "lastName-eq" -> author.lastName.eq(value)
                "lastName-like" -> author.lastName.like(value)
                "email-eq" -> author.email.eq(value)
                "email-like" -> author.email.like(value)

     */

    override fun toString(): String {
        return qname
    }

}

/*
fun FilterField.like(source:BooleanExpression):BooleanExpression {
    source.orAllOf()
}
*/

/*
fun FilterField.toQueryDsl(value:String):BooleanExpression {
    val x:List<String> = listOf()
    //ExpressionUtils.all<T>(right)
    return when(this) {
        FilterField.authorFirstName_eqAll -> QAuthor.author.firstName.`in`()
        else -> throw RuntimeException("QDSL UNSUPPORTED SYNTAX!")
    }
}
*/


/*
fun StringPath.eqAll(source: BooleanExpression, value: List<String>): BooleanExpression {
    if (value.isEmpty()) {
        return source
    }
    var sink: BooleanExpression = source
    value.forEach {
        val str = it
        sink = sink.and(this.eq(str))
    }


    return sink
}

fun StringPath.eqAny(source: BooleanExpression, value: List<String>): BooleanExpression {
    if (value.isEmpty()) {
        return source
    }
    var sink: BooleanExpression = source
    value.forEach {
        val str = it
        sink = sink.or(this.eq(str))
    }
    return sink
}
Ü*/