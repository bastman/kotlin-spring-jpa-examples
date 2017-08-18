package com.example.demo.api.tweeter.domain.services

import com.example.demo.api.common.EntityNotFoundException
import com.example.demo.api.tweeter.domain.entities.Author
import com.example.demo.api.tweeter.domain.entities.Tweet
import com.example.demo.api.tweeter.domain.repositories.AuthorRepository
import com.example.demo.api.tweeter.domain.repositories.TweetRepository
import com.example.demo.util.optionals.toNullable
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
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

    fun findAll(pageRequest: PageRequest): Page<Author> {
        return authorRepository.findAll(pageRequest)
    }

    fun getById(authorId: UUID): Author {
        return findById(authorId) ?: throw EntityNotFoundException(
                "ENTITY NOT FOUND! query: author.id=$authorId"
        )
    }
}