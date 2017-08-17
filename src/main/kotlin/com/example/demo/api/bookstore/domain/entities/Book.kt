package com.example.demo.api.bookstore.domain.entities

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "bookstore", type = "books")
class Book {

    @Id
    var id: String? = null

    var title: String? = null

    var author: String? = null

    var releaseDate: String? = null

    constructor() {}

    constructor(id: String, title: String, author: String, releaseDate: String) {
        this.id = id
        this.title = title
        this.author = author
        this.releaseDate = releaseDate
    }

    override fun toString(): String {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}'
    }
}

