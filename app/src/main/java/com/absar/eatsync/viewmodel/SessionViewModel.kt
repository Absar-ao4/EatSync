package com.absar.eatsync.viewmodel

import androidx.lifecycle.ViewModel
import com.absar.eatsync.model.Participant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SessionViewModel : ViewModel() {

    private val _participants=MutableStateFlow<List<Participant>>(emptyList())
    val participants:StateFlow<List<Participant>> =_participants.asStateFlow()

    fun createLocalSession(hostName:String){
        _participants.value=listOf(
            Participant(
                userId = "user_host",
                name = hostName,
                isHost = true,
                isReady = false
            ),
            Participant(
                userId = "user_lala",
                name = "Lala",
                isHost = false,
                isReady = false
            ),
            Participant(
                userId = "user_avro",
                name = "Avro",
                isHost = false,
                isReady = false
            ),
            Participant(
                userId = "user_ansh",
                name = "Ansh",
                isHost = false,
                isReady = false
            ),
            Participant(
                userId = "user_aryan",
                name = "Aryan",
                isHost = false,
                isReady = false
            )
        )
    }

    fun joinLocalSession(userName: String){
        _participants.value=listOf(
            Participant(
                userId = "user_host",
                name = "Absar",
                isHost = true,
                isReady = false
            ),
            Participant(
                userId = "user_joined",
                name = userName,
                isHost = false,
                isReady = false
            ),
            Participant(
                userId = "user_lala",
                name = "Lala",
                isHost = false,
                isReady = false
            ),
            Participant(
                userId = "user_avro",
                name = "Avro",
                isHost = false,
                isReady = false
            )
        )
    }

    fun toggleReady(userName:String){
        _participants.value=_participants.value.map{participant->
            if(participant.name==userName) {
                participant.copy(isReady=!participant.isReady)
            }else{
                participant
            }
        }
    }
}