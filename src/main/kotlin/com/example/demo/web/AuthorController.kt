package com.example.demo.web

import com.example.demo.jpa.Author
import com.example.demo.jpa.AuthorRepository
import com.example.demo.jpa.JpaAuthorService
import com.example.demo.logging.AppLogger
import com.example.demo.util.optionals.toNullable
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import javax.persistence.EntityNotFoundException
import javax.validation.constraints.Size

@RestController
class AuthorController(
        private val authorRepository: AuthorRepository,
        private val jpaAuthorService:JpaAuthorService
) {

    @PostMapping("/authors", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun create(
            @Validated @RequestBody request: CreateRequest
    ): Any? {
        val now = Instant.now()
        val entity = Author(
                id = UUID.randomUUID(),
                createdAt = now,
                modifiedAt = now,
                email = request.email,
                firstName = request.firstName,
                lastName = request.lastName
        )
        val savedEntity = authorRepository.save(entity)

        return savedEntity
    }

    @PostMapping("/authors/{authorId}", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun update(
            @PathVariable authorId: UUID,

            @Validated
            @RequestBody request: UpdateRequest
    ): Any? {
        LOG.info("update() authorId=$authorId payload=$request")
        val oldEntity = authorRepository
                .getById(authorId)
                .toNullable() ?: throw EntityNotFoundException("author not found! author.id=$authorId")

        val modifiedEntity = oldEntity.copy(
                email = request.email,
                firstName = request.firstName,
                lastName = request.lastName,
                modifiedAt = Instant.now()
        )

        val savedEntity = authorRepository.save(modifiedEntity)
      //  val savedEntity = jpaAuthorService.save(modifiedEntity)

        LOG.info("update() DONE. authorId=$authorId savedEntity=$savedEntity")

        return savedEntity
    }

    @GetMapping("/authors/{authorId}")
    fun getOne(
            @PathVariable authorId: UUID
    ): Any? {
        val entity = authorRepository
                .getById(authorId)
                .toNullable()

        return entity ?: throw EntityNotFoundException("author not found! author.id=$authorId")
    }


    data class CreateRequest(
            @get:Size(min = 5, max = 15)
            val email: String,
            @get:NotBlank @get:Size(min = 5, max = 15)
            val firstName: String,
            @get:NotBlank
            val lastName: String
    )

    data class UpdateRequest(
            @get:Size(min = 5, max = 15)
            val email: String,
            @get:NotBlank @get:Size(min = 5, max = 15)
            val firstName: String,
            @get:NotBlank
            val lastName: String
    )

    companion object {
        private val LOG = AppLogger(this::class)
    }
}

