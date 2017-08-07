package com.example.demo.web.author.querydsl

import com.example.demo.jpa.QAuthor
import com.fasterxml.jackson.annotation.JsonValue
import com.querydsl.core.types.OrderSpecifier
import jdk.nashorn.internal.objects.annotations.Where
import java.time.Instant

data class QueryDslRequest(
        val filterBy:String,
        val orderBy:List<SortableField>
)

enum class SortableField(
        @get:JsonValue val qname:String,
        val queryDsl: OrderSpecifier<*>
) {
    AUTHOR_FIRST_NAME_ASC("author.firstName-ASC", QAuthor.author.firstName.asc()),
    AUTHOR_FIRST_NAME_DESC("author.firstName-DESC", QAuthor.author.firstName.desc()),
    AUTHOR_LAST_NAME_ASC("author.lastName-ASC", QAuthor.author.lastName.asc()),
    AUTHOR_LAST_NAME_DESC("author.lastName-DESC", QAuthor.author.lastName.desc()),
    AUTHOR_EMAIL_ASC("author.email-ASC", QAuthor.author.email.asc()),
    AUTHOR_EMAIL_DESC("author.email-DESC", QAuthor.author.email.desc()),

    AUTHOR_MODIFIED_AT_ASC("author.modifiedAt-ASC", QAuthor.author.modifiedAt.asc()),
    AUTHOR_MODIFIED_AT_DESC("author.modifiedAt-DESC", QAuthor.author.modifiedAt.desc())

    ;

    override fun toString(): String {
        return qname
    }
}

