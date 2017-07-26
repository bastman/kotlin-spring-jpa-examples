package com.example.demo.domain.tweet

import com.example.demo.domain.EntityNotFoundException
import com.example.demo.jpa.Tweet
import com.example.demo.jpa.TweetRepository
import com.example.demo.util.optionals.toNullable
import org.springframework.stereotype.Component
import java.util.*
import javax.validation.Valid

@Component
class JpaTweetService(
        private val tweetRepository: TweetRepository

) {
    fun save(@Valid tweet: Tweet): Tweet {
        return tweetRepository.save(tweet)
    }

    fun findById(tweetId: UUID): Tweet? {
        return tweetRepository
                .getById(tweetId)
                .toNullable()
    }

    fun getById(tweetId: UUID): Tweet {
        return findById(tweetId) ?: throw EntityNotFoundException(
                "ENTITY NOT FOUND! query: tweet.id=$tweetId"
        )
    }
}