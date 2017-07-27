package com.example.demo.web.tweet

import com.example.demo.domain.author.JpaAuthorService
import com.example.demo.domain.tweet.JpaTweetService
import com.example.demo.jpa.Author
import com.example.demo.jpa.Tweet
import com.example.demo.util.fp.pipe
import io.swagger.annotations.ApiModel
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
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

    @GetMapping("/tweets/{tweetId}")
    fun getOne(
            @PathVariable tweetId: UUID
    ): Any? {
        val entity: Tweet = jpaTweetService
                .getById(tweetId)

        return entity
    }

    @PostMapping("/tweets/{tweetId}")
    fun update(
            @PathVariable tweetId: UUID,
            @Validated @RequestBody request: UpdateRequest
    ): Any? {
        val sourceTweet: Tweet = jpaTweetService.getById(tweetId = tweetId)

        val sinkTweet: Tweet = sourceTweet
                .pipe {
                    if (request.authorId != null) {
                        val author: Author = jpaAuthorService.getById(authorId = request.authorId)
                        it.copy(author = author)
                    } else { it }
                } pipe {
                    if (request.message != null) {
                        it.copy(message = request.message)
                    } else { it }
                } pipe {
                    val isModified = it != sourceTweet
                    if (isModified) {
                        jpaTweetService.save(it.copy(modifiedAt = Instant.now()))
                    } else { it }
                }

        return sinkTweet
    }


    @ApiModel(value = "Tweet.CreateRequest")
    data class CreateRequest(
            val authorId: UUID,
            val message: String
    )

    @ApiModel(value = "Tweet.UpdateRequest")
    data class UpdateRequest(
            val authorId: UUID?,
            val message: String?
    )
}

