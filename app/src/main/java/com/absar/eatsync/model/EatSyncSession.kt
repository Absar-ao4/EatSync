package com.absar.eatsync.model

data class EatSyncSession(
    val sessionCode: String = "",
    val hostName: String = "",
    val status: String = "WAITING",
    val cartLocked: Boolean = false,
    val createdAt: Long = 0L
)