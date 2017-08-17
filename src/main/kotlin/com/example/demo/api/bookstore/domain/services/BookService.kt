package com.example.demo.api.bookstore.domain.services

import com.example.demo.api.bookstore.domain.entities.Book
import com.example.demo.api.bookstore.domain.repositories.EsBookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BookService(
        private val bookRepository: EsBookRepository
) {

    fun save(book: Book): Book {
        return bookRepository.save(book)
    }

    fun delete(book: Book) {
        bookRepository.delete(book)
    }

    fun findOne(id: String): Book {
        return bookRepository.findOne(id)
    }

    fun findAll(): Iterable<Book> {
        return bookRepository.findAll()
    }

    fun findByAuthor(author: String, pageRequest: PageRequest): Page<Book> {
        return bookRepository.findByAuthor(author, pageRequest)
    }

    fun findByTitle(title: String): List<Book> {
        return bookRepository.findByTitle(title)
    }

}