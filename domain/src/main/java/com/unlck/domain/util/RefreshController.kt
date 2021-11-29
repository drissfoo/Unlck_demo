package com.unlck.domain.util

import java.util.*
import java.util.concurrent.TimeUnit

class RefreshController(
    rate: Long = DEFAULT_REFRESH_RATE_MS,
) {

    companion object {
        val DEFAULT_REFRESH_RATE_MS = TimeUnit.MINUTES.toMillis(5)
    }

    var refreshRate: Long = rate
    private var lastUpdateDate: Date? = null

    fun refresh() {
        lastUpdateDate = Date()
    }

    fun isExpired() = lastUpdateDate?.let { (Date().time - it.time) > refreshRate } ?: true
}