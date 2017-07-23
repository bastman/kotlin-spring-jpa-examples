package com.example.demo.jpa

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
interface TweetRepository : JpaRepository<Tweet, UUID>