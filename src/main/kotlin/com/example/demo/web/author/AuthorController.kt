package com.example.demo.web.author

import com.example.demo.domain.author.JpaAuthorService
import com.example.demo.jpa.Author
import com.example.demo.jpa.AuthorRepository
import com.example.demo.logging.AppLogger
import com.example.demo.web.common.Pagination
import io.swagger.annotations.ApiModel
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import javax.validation.constraints.Size

import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.data.querydsl.binding.QuerydslPredicate
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestMapping

//import com.mysema.query.jpa.impl.JPAQuery
import javax.persistence.EntityManager
import com.querydsl.core.types.Predicate


@RestController
class AuthorController(
        private val jpaAuthorService: JpaAuthorService,
        private val jpaAuthorRepository: AuthorRepository,
        private val entityManager: EntityManager
) {

    @PostMapping("/authors", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun create(
            @Validated
            @RequestBody request: CreateRequest
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

    @GetMapping("/authors")
    fun findAll(
            @RequestParam(defaultValue = "0")
            page: Int,
            @RequestParam(defaultValue = "20")
            pageSize: Int,
            @RequestParam(defaultValue = "firstName")
            sortedBy: String,
            @RequestParam(defaultValue = "DESC")
            sortDirection: Sort.Direction
    ): Any? {
        val pageRequest = PageRequest(page, pageSize, sortDirection, sortedBy)
        val pageResult = jpaAuthorService.findAll(pageRequest)
        val response = FindAllResponse(
                authors = pageResult.content.toList(),
                pagination = Pagination.ofPageResult(pageResult)
        )

        return response
    }

    @GetMapping("/authors/search")
    fun search(
            @RequestParam(defaultValue = "0")
            page: Int,
            @RequestParam(defaultValue = "20")
            pageSize: Int,
            @RequestParam(defaultValue = "firstName")
            sortedBy: String,
            @RequestParam(defaultValue = "DESC")
            sortDirection: Sort.Direction
    ): Any? {
        val pageRequest = PageRequest(page, pageSize, sortDirection, sortedBy)
        val pageResult = jpaAuthorService.findAll(pageRequest)
        val response = FindAllResponse(
                authors = pageResult.content.toList(),
                pagination = Pagination.ofPageResult(pageResult)
        )

        return response
    }

    @RequestMapping(value = "/search", method = arrayOf(RequestMethod.GET))
    fun index(@QuerydslPredicate(root = Author::class) predicate: Predicate,
              pageable: Pageable, @RequestParam parameters: MultiValueMap<String, String>): Any? {

        /*
        val query = JPAQuery(entityManager)

        val uniqueUserNames = query.from(au)
                .where(user.name.like("%ov"))
                .groupBy(user.name)
                .list(user.name)
                */
        val r= jpaAuthorRepository.findAll(predicate, pageable)

        return r
    }

    data class FindAllResponse(
            val authors: List<Author>,
            val pagination: Pagination
    )


    @ApiModel(value = "Author.CreateRequest")
    data class CreateRequest(
            @get:[NotBlank Email]
            val email: String,
            @get:[NotBlank Size(min = 5, max = 15)]
            val firstName: String,
            @get:[NotBlank Size(min = 5, max = 15)]
            val lastName: String
    )

    @ApiModel(value = "Author.UpdateRequest")
    data class UpdateRequest(
            @get:[NotBlank Email]
            val email: String,
            @get:[NotBlank Size(min = 5, max = 15)]
            val firstName: String,
            @get:[NotBlank Size(min = 5, max = 15)]
            val lastName: String
    )

    companion object {
        private val LOG = AppLogger(this::class)
    }
}

