package com.example.demo.api.realestate.domain.jpa.services

import com.example.demo.api.common.EntityAlreadyExistException
import com.example.demo.api.common.EntityNotFoundException
import com.example.demo.api.realestate.domain.jpa.entities.Property
import com.example.demo.api.realestate.domain.jpa.entities.PropertyCluster
import com.example.demo.api.realestate.domain.jpa.repositories.PropertyClusterRepository
import com.example.demo.util.optionals.toNullable
import org.springframework.stereotype.Component
import java.util.*
import javax.validation.Valid

@Component
class JpaPropertyClusterService(
        private val propertyClusterRepository: PropertyClusterRepository
) {
    fun exists(clusterId: UUID): Boolean = propertyClusterRepository.exists(clusterId)

    fun findById(clusterId: UUID): PropertyCluster? =
        propertyClusterRepository
                .getById(clusterId)
                .toNullable()

    fun getById(clusterId: UUID): PropertyCluster =
        findById(clusterId) ?: throw EntityNotFoundException(
                "ENTITY NOT FOUND! query: PropertyCluster.id=$clusterId"
        )

    fun requireExists(clusterId: UUID): UUID =
        if (exists(clusterId)) {
            clusterId
        } else throw EntityNotFoundException(
                "ENTITY NOT FOUND! query: PropertyCluster.id=$clusterId"
        )

    fun requireDoesNotExist(clusterId: UUID): UUID =
        if (!exists(clusterId)) {
            clusterId
        } else throw EntityAlreadyExistException(
                "ENTITY ALREADY EXIST! query: PropertyCluster.id=$clusterId"
        )

    fun insert(@Valid propertyCluster: PropertyCluster): PropertyCluster {
        requireDoesNotExist(propertyCluster.id)

        return propertyClusterRepository.save(propertyCluster)
    }

    fun update(@Valid propertyCluster: PropertyCluster): PropertyCluster {
        requireExists(propertyCluster.id)

        return propertyClusterRepository.save(propertyCluster)
    }
}