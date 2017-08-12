package com.example.demo.api.tweeter.domain

import com.example.demo.api.tweeter.domain.Author
import com.example.demo.api.tweeter.domain.Tweet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
//@Transactional(Transactional.TxType.MANDATORY)
interface AuthorRepository : JpaRepository<Author, UUID> {
    fun getById(id: UUID): Optional<Author>
}


@Repository
//@Transactional(Transactional.TxType.MANDATORY)
interface TweetRepository : JpaRepository<Tweet, UUID> {
    fun getById(id: UUID): Optional<Tweet>
}