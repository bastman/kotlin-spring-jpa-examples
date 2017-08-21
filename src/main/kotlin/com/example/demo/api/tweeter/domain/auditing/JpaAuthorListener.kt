package com.example.demo.api.tweeter.domain.auditing

import com.example.demo.api.tweeter.domain.entities.Author
import com.example.demo.api.tweeter.domain.services.EsAuthorService
import com.example.demo.logging.AppLogger
import org.springframework.stereotype.Component
import javax.inject.Inject
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@Component
class JpaAuthorListener() {
    @PrePersist
    fun beforeInsert(o: Author) {
        LOG.info("audit: preInsert $o")
    }

    @PreUpdate
    fun beforeUpdate(o: Author) {
        LOG.info("audit: preUpdate $o")
    }

    companion object {
        private val LOG = AppLogger(this::class)
    }
}