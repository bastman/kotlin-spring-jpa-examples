package com.example.demo.web.tweet

import com.example.demo.domain.author.JpaAuthorService
import com.example.demo.domain.tweet.JpaTweetService
import com.example.demo.jpa.Author
import com.example.demo.jpa.Tweet
import io.swagger.annotations.ApiModel
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
class TweetController(
        private val jpaTweetService: JpaTweetService,
        private val jpaAuthorService: JpaAuthorService
) {

    @PostMapping("/tweets", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun create(
            @Validated @RequestBody request: CreateRequest
    ): Any? {
        val author: Author = jpaAuthorService.getById(authorId = request.authorId)

        val now = Instant.now()
        val tweet = Tweet(
                id = UUID.randomUUID(),
                createdAt = now,
                modifiedAt = now,
                author = author,
                message = request.message
        )
        val savedEntity = jpaTweetService.save(tweet)

        return savedEntity
    }


    @ApiModel(value = "Tweet.CreateRequest")
    data class CreateRequest(
            val authorId: UUID,
            val message: String
    )
}