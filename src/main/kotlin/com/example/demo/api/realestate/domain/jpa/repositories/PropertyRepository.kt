package com.example.demo.api.realestate.domain.jpa.repositories


import com.example.demo.api.realestate.domain.jpa.entities.Property
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PropertyRepository : JpaRepository<Property, UUID> {
    fun getById(id: UUID): Optional<Property>
}