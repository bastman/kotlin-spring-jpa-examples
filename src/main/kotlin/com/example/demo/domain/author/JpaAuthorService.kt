package com.example.demo.domain.author

import com.example.demo.domain.EntityNotFoundException
import com.example.demo.jpa.Author
import com.example.demo.jpa.AuthorRepository
import com.example.demo.util.optionals.toNullable
import org.springframework.stereotype.Component
import java.util.*
import javax.validation.Valid

@Component
class JpaAuthorService(
        private val authorRepository: AuthorRepository
) {
    fun save(@Valid author: Author): Author {
        return authorRepository.save(author)
    }

    fun findById(authorId: UUID): Author? {
        return authorRepository
                .getById(authorId)
                .toNullable()
    }

    fun getById(authorId: UUID): Author {
        return findById(authorId) ?: throw EntityNotFoundException(
                "ENTITY NOT FOUND! query: author.id=$authorId"
        )
    }
}