package com.example.demo.api.tweeter.domain.repositories

import com.example.demo.api.tweeter.domain.entities.Author
import com.example.demo.api.tweeter.domain.entities.Tweet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
//@Transactional(Transactional.TxType.MANDATORY)
interface AuthorRepository :
        JpaRepository<Author, UUID>
//CrudRepository<Author, UUID>
{
    fun getById(id: UUID): Optional<Author>
    //override fun findAll(): Iterable<Author>
}


@Repository
//@Transactional(Transactional.TxType.MANDATORY)
interface TweetRepository : JpaRepository<Tweet, UUID> {
    fun getById(id: UUID): Optional<Tweet>
}