package com.absar.eatsync.model

data class Participant(
    val userId: String,
    val name: String,
    val isHost: Boolean=false,
    val isReady: Boolean=false
)