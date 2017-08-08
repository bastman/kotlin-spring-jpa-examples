package com.example.demo.web.author

import com.example.demo.domain.author.JpaAuthorService
import com.example.demo.jpa.Author
import com.example.demo.jpa.QAuthor
import com.example.demo.logging.AppLogger
import com.example.demo.querydsl.andAllOf
import com.example.demo.querydsl.andAnyOf
import com.example.demo.util.fp.pipe
import com.example.demo.web.author.querydsl.*

import com.example.demo.web.common.Pagination
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
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
            @RequestBody req:QueryDslRequest
    ): Any? {
        val offset: Long = 0
        val limit: Long = 100
        val query = JPAQuery<Void>(entityManager)
        val author = QAuthor.author

        val filters = req.filter?.map {
            val field=it.field
            val value = it.value
            when(field) {
                FilterField.author_firstName_eq -> author.firstName.eq(value)
                FilterField.author_firstName_like -> author.firstName.like(value)
                FilterField.author_lastName_eq -> author.lastName.eq(value)
                FilterField.author_lastName_like -> author.lastName.like(value)
                else->throw RuntimeException("BadRequest! FILTER field=$field")
            }
        }?: emptyList()

        val search = req.search?.map {
            val field=it.field
            val value = it.value
            when(field) {
                FilterField.author_firstName_eq -> author.firstName.eq(value)
                FilterField.author_firstName_like -> author.firstName.like(value)
                FilterField.author_lastName_eq -> author.lastName.eq(value)
                FilterField.author_lastName_like -> author.lastName.like(value)
                else->throw RuntimeException("BadRequest! SEARCH field=$field")
            }
        }?: emptyList()



        val p = author.firstName.like("%%")

        val resultSet = query.from(author)
                .where(

                        author.isNotNull
                                .andAllOf(filters)
                                .andAnyOf(search)
                )

                .pipe {
                    val expressions=req.orderBy.map { field->field.toQueryDsl() }.toTypedArray()
                    if(expressions.isEmpty()){
                        it
                    } else {
                        it.orderBy(*expressions)
                    }
                }
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

