package com.example.demo.api.tweeter.domain.services

import com.example.demo.api.tweeter.domain.entities.Author
import com.example.demo.api.tweeter.domain.entities.Tweet
import com.example.demo.es.EsClientService
import org.springframework.stereotype.Component

@Component
class EsAuthorService(
        private val esClientService: EsClientService
) {

    fun put(author: Author) {
        esClientService.put("/tweeter/author/${author.id}", author)
    }
}

@Component
class EsTweetService(
        private val esClientService: EsClientService
) {

    fun put(tweet: Tweet) {
        esClientService.put("/tweeter/tweet/${tweet.id}", tweet)
    }
}