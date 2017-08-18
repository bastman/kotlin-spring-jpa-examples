package com.example.demo.api.bookstore.domain.entities

import org.springframework.data.annotation.Id
import java.util.*


data class Book(
        @Id
        var id: String? = null,

        // https://stackoverflow.com/questions/32042430/elasticsearch-spring-data-date-format-always-is-long
        // http://www.baeldung.com/jackson-serialize-dates

        //@field: JsonFormat(shape = JsonFormat.Shape.STRING)
        //@field: Temporal(TemporalType.TIMESTAMP)
        //@JsonFormat (shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZZ")

        //@Field(type = FieldType.Date, format = DateFormat.date_optional_time)
        //@JsonProperty(value = "@timestamp")
        //@Field(type = FieldType.Date, index = FieldIndex.not_analyzed, store = true, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")

        //@JsonSerialize(using = InstantSerializer::class)
        //@JsonFormat (shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
        //@JsonFormat (shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
        //@JsonFormat (shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
        //@Field(type = FieldType.Date, index = FieldIndex.not_analyzed, store = true, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")

        //var modifiedAt:Instant?=null,


        //@Field(type = FieldType.Date)
        var modifiedAt: Date? = null,

        var title: String? = null,

        var author: String? = null,

        var releaseDate: String? = null
) {


    //constructor() {}


/*
    override fun toString(): String {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}'
    }
    */
}

