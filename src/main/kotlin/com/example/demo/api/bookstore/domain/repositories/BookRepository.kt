package com.example.demo.api.bookstore.domain.repositories

import com.example.demo.api.bookstore.domain.entities.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface EsBookRepository : ElasticsearchRepository<Book, String> {
    fun findByAuthor(author: String, pageable: Pageable): Page<Book>
    fun findByTitle(title: String): List<Book>
}

