package com.example.demo.api.tweeter.domain.auditing

import com.example.demo.api.tweeter.domain.entities.Author
import com.example.demo.api.tweeter.domain.entities.Tweet
import com.example.demo.logging.AppLogger
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

class JpaTweetListener {
    @PrePersist
    fun beforeInsert(o: Tweet) {
        LOG.info("audit: preInsert $o")
    }

    @PreUpdate
    fun beforeUpdate(o: Tweet) {
        LOG.info("audit: preUpdate $o")
    }

    companion object {
        private val LOG = AppLogger(this::class)
    }
}