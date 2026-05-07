package com.absar.eatsync.model

data class Participant(
    val userId:String="",
    val name:String="",
    val host:Boolean=false,
    val ready:Boolean=false
)