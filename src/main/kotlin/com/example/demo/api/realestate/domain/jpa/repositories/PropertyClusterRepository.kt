package com.example.demo.api.realestate.domain.jpa.repositories

import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.domain.jpa.entities.PropertyCluster
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PropertyClusterRepository : JpaRepository<PropertyCluster, UUID> {
    fun getById(id: UUID): Optional<PropertyCluster>
}