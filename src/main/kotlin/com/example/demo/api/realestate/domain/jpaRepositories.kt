package com.example.demo.api.realestate.domain

import com.example.demo.api.common.EntityNotFoundException
import com.example.demo.api.tweeter.domain.Author
import com.example.demo.api.tweeter.domain.AuthorRepository
import com.example.demo.api.tweeter.domain.Tweet
import com.example.demo.util.optionals.toNullable
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*
import javax.validation.Valid

@Repository
interface PropertyRepository : JpaRepository<Property, UUID> {
    fun getById(id: UUID): Optional<Property>
}

