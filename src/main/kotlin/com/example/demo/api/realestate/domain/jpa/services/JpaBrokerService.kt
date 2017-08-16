package com.example.demo.api.realestate.domain.jpa.services

import com.example.demo.api.common.EntityAlreadyExistException
import com.example.demo.api.common.EntityNotFoundException
import com.example.demo.api.realestate.domain.jpa.entities.Broker
import com.example.demo.api.realestate.domain.jpa.entities.QueryDslEntity.qBroker
import com.example.demo.api.realestate.domain.jpa.repositories.BrokerRepository
import com.example.demo.util.fp.pipe
import com.example.demo.util.optionals.toNullable
import com.querydsl.jpa.impl.JPAQuery
import org.springframework.stereotype.Component
import java.util.*
import javax.persistence.EntityManager
import javax.validation.Valid

@Component
class JpaBrokerService(
        private val brokerRepository: BrokerRepository,
        private val entityManager: EntityManager
) {
    fun exists(brokerId: UUID): Boolean = brokerRepository.exists(brokerId)

    fun findById(brokerId: UUID): Broker? =
            brokerRepository
                    .getById(brokerId)
                    .toNullable()

    fun getById(brokerId: UUID): Broker =
            findById(brokerId) ?: throw EntityNotFoundException(
                    "ENTITY NOT FOUND! query: Broker.id=$brokerId"
            )

    fun requireExists(brokerId: UUID): UUID =
            if (exists(brokerId)) {
                brokerId
            } else throw EntityNotFoundException(
                    "ENTITY NOT FOUND! query: Broker.id=$brokerId"
            )

    fun requireDoesNotExist(brokerId: UUID): UUID =
            if (!exists(brokerId)) {
                brokerId
            } else throw EntityAlreadyExistException(
                    "ENTITY ALREADY EXIST! query: Broker.id=$brokerId"
            )

    fun insert(@Valid broker: Broker): Broker =
            requireDoesNotExist(broker.id) pipe { brokerRepository.save(broker) }

    fun update(@Valid broker: Broker): Broker =
            requireExists(broker.id) pipe { brokerRepository.save(broker) }

    fun findByIdList(brokerIdList: List<UUID>): List<Broker> {
        val query = JPAQuery<Broker>(entityManager)
        val resultSet = query.from(qBroker)
                .where(
                        qBroker.id.`in`(brokerIdList)
                )
                .fetchResults()

        return resultSet.results
    }
}