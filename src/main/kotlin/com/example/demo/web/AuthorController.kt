package com.example.demo.web

import com.example.demo.jpa.Author
import com.example.demo.jpa.AuthorRepository
import com.example.demo.util.optionals.toNullable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
class AuthorController(
        private val authorRepository: AuthorRepository
) {

    @PostMapping("/author")
    fun create(
            email: String,
            firstName: String,
            lastName: String
    ): Any? {
        val now = Instant.now()
        val entity = Author(
                id = UUID.randomUUID(),
                createdAt = now,
                modifiedAt = now,
                email = email,
                firstName = firstName,
                lastName = lastName
        )
        val savedEntity = authorRepository.save(entity)

        return savedEntity
    }

    @GetMapping("/author/{authorId}")
    fun getOne(
            @PathVariable authorId: UUID
    ): Any? {
        val r = authorRepository
                .getById(authorId)
                .toNullable()

        return r
    }
}

