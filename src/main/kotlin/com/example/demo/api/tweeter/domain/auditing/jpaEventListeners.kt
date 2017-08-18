package com.example.demo.api.tweeter.domain.auditing

import com.example.demo.logging.AppLogger
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

class MyAuditListener() {
    @PrePersist
    fun beforeInsert(o: Any) {
        LOG.info("audit: preInsert $o")
    }

    @PreUpdate
    fun beforeUpdate(o: Any) {
        LOG.info("audit: preUpdate $o")
    }

    companion object {
        private val LOG = AppLogger(this::class)
    }
}