package com.example.demo.api.tweeter

import com.example.demo.api.tweeter.domain.JpaAuthorService
import com.example.demo.api.tweeter.domain.Author
import com.example.demo.jpa.QAuthor
import com.example.demo.logging.AppLogger
import com.example.demo.querydsl.andAllOf
import com.example.demo.querydsl.andAnyOf
import com.example.demo.querydsl.orderBy
import com.example.demo.util.fp.pipe
import com.example.demo.api.tweeter.handler.author.search.QueryDslRequest
import com.example.demo.api.common.Pagination
import com.querydsl.jpa.impl.JPAQuery
import io.swagger.annotations.ApiModel
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import javax.persistence.EntityManager
import javax.validation.constraints.Size

@RestController
class AuthorController(
        private val jpaAuthorService: JpaAuthorService,
        private val entityManager: EntityManager
) {

    @PostMapping("/authors/querydsl/jpa")
    fun jpaQuerydslExample(
            @RequestBody req: QueryDslRequest
    ): Any? {
        val offset: Long = req.offset ?: 0
        val limit: Long = req.limit ?: 100

        val filters = req.filter?.map {
            it.field.toBooleanExpression(it.value)
        } ?: emptyList()

        val search = req.search?.map {
            it.field.toBooleanExpression(it.value)
        } ?: emptyList()

        val order = req.orderBy?.map {
            it.toOrderSpecifier()
        } ?: emptyList()

        val query = JPAQuery<Void>(entityManager)
        val author = QAuthor.author

        val resultSet = query.from(author)
                .where(
                        author.isNotNull
                                .andAllOf(filters)
                                .andAnyOf(search)
                ).orderBy(order)
                .offset(offset)
                .limit(limit)
                .fetchResults()

        return resultSet
    }


    @PostMapping("/authors", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun create(@RequestBody @Validated request: CreateRequest): Any? {
        val author = Author(
                id = UUID.randomUUID(),
                createdAt = Instant.EPOCH,
                modifiedAt = Instant.EPOCH,
                email = request.email,
                firstName = request.firstName,
                lastName = request.lastName
        ) pipe {
            jpaAuthorService.save(it)
        }

        return author
    }

    @PostMapping("/authors/{authorId}", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun update(
            @PathVariable authorId: UUID,
            @RequestBody @Validated request: UpdateRequest
    ): Any? {
        LOG.info("update() authorId=$authorId payload=$request")
        val sourceAuthor: Author = jpaAuthorService
                .getById(authorId)

        val sinkAuthor: Author =
                sourceAuthor.copy(
                        email = request.email,
                        firstName = request.firstName,
                        lastName = request.lastName
                ) pipe {
                    val isChanged = it != sourceAuthor
                    if (isChanged) {
                        jpaAuthorService.save(it)
                    } else {
                        it
                    }
                }

        return sinkAuthor
    }

    @GetMapping("/authors/{authorId}")
    fun getOne(@PathVariable authorId: UUID): Any? {
        val author: Author = jpaAuthorService.getById(authorId)

        return author
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

