package com.example.demo.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.validation.annotation.Validated
import java.util.*
import javax.validation.Valid

@Repository
//@Transactional(Transactional.TxType.MANDATORY)
interface AuthorRepository : JpaRepository<Author, UUID> {
    fun getById(id: UUID): Optional<Author>
}

@Component
class JpaAuthorService(
        private val authorRepository: AuthorRepository
) {
    fun save(@Valid author: Author):Author {
        return authorRepository.save(author)
    }
}

@Repository
//@Transactional(Transactional.TxType.MANDATORY)
interface TweetRepository : JpaRepository<Tweet, UUID>