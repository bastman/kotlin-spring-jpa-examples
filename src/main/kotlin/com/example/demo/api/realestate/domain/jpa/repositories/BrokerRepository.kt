package com.example.demo.api.realestate.domain.jpa.repositories

import com.example.demo.api.realestate.domain.jpa.entities.Broker
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BrokerRepository : JpaRepository<Broker, UUID> {
    fun getById(id: UUID): Optional<Broker>
}