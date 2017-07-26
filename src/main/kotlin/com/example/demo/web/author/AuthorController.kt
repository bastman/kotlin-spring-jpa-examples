package com.example.demo.web.author

import com.example.demo.domain.author.JpaAuthorService
import com.example.demo.jpa.Author
import com.example.demo.logging.AppLogger
import org.hibernate.validator.constraints.NotBlank
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import javax.validation.constraints.Size

@RestController
class AuthorController(
        private val jpaAuthorService: JpaAuthorService
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
        val savedEntity = jpaAuthorService.save(entity)

        return savedEntity
    }

    @PostMapping("/authors/{authorId}", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun update(
            @PathVariable authorId: UUID,

            @Validated
            @RequestBody request: UpdateRequest
    ): Any? {
        LOG.info("update() authorId=$authorId payload=$request")
        val oldEntity: Author = jpaAuthorService
                .getById(authorId)

        val modifiedEntity = oldEntity.copy(
                email = request.email,
                firstName = request.firstName,
                lastName = request.lastName,
                modifiedAt = Instant.now()
        )

        val savedEntity = jpaAuthorService.save(modifiedEntity)

        LOG.info("update() DONE. authorId=$authorId savedEntity=$savedEntity")

        return savedEntity
    }

    @GetMapping("/authors/{authorId}")
    fun getOne(
            @PathVariable authorId: UUID
    ): Any? {
        val entity: Author = jpaAuthorService
                .getById(authorId)

        return entity
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

