package com.absar.eatsync.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.absar.eatsync.firebase.FirebaseSessionManager
import com.absar.eatsync.model.Participant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SessionViewModel : ViewModel() {

    private val firebaseSessionManager = FirebaseSessionManager()

    private val _participants = MutableStateFlow<List<Participant>>(emptyList())
    val participants: StateFlow<List<Participant>> = _participants.asStateFlow()

    private val _currentSessionCode = MutableStateFlow<String?>(null)
    val currentSessionCode: StateFlow<String?> = _currentSessionCode.asStateFlow()
    private val _currentUserName = MutableStateFlow("")
    val currentUserName: StateFlow<String> = _currentUserName.asStateFlow()

    fun createFirebaseSession(
        sessionCode: String,
        hostName: String
    ){
        viewModelScope.launch{
            try{
                Log.d("EatSyncFirebase", "createFirebaseSession called")
                Log.d("EatSyncFirebase", "Session Code: $sessionCode, Host: $hostName")
                _currentSessionCode.value = sessionCode
                _currentUserName.value = hostName
                firebaseSessionManager.createSession(
                    sessionCode=sessionCode,
                    hostName=hostName
                )
                Log.d("EatSyncFirebase", "Session written to Firebase")
                observeParticipants(sessionCode)

            }
            catch(e:Exception){
                Log.e("EatSyncFirebase", "Error creating Firebase session", e)
            }
        }
    }

    fun joinFirebaseSession(
        sessionCode: String,
        userName: String
    ){
        viewModelScope.launch{
            try{
                Log.d("EatSyncFirebase", "joinFirebaseSession called")
                Log.d("EatSyncFirebase", "Session Code: $sessionCode, User: $userName")
                _currentSessionCode.value = sessionCode
                _currentUserName.value = userName
                firebaseSessionManager.joinSession(
                    sessionCode=sessionCode,
                    userName=userName
                )
                Log.d("EatSyncFirebase", "User joined Firebase session")
                observeParticipants(sessionCode)
            }catch(e: Exception){
                Log.e("EatSyncFirebase", "Error joining Firebase session", e)
            }
        }
    }

    private fun observeParticipants(sessionCode: String){
        viewModelScope.launch{
            try{
                Log.d("EatSyncFirebase", "observeParticipants started for $sessionCode")
                firebaseSessionManager.observeParticipants(sessionCode)
                    .collect{participantList->
                        Log.d(
                            "EatSyncFirebase",
                            "Participants updated: ${participantList.size}"
                        )
                        _participants.value = participantList
                    }
            }catch(e:Exception){
                Log.e("EatSyncFirebase", "Error observing participants", e)
            }
        }
    }

    fun toggleReady(userName: String){
        val sessionCode= _currentSessionCode.value

        if(sessionCode==null){
            Log.e("EatSyncFirebase", "toggleReady failed: sessionCode is null")
            return
        }
        val participant= _participants.value.firstOrNull{
            it.name==userName
        }
        if(participant == null){
            Log.e("EatSyncFirebase", "toggleReady failed: participant not found")
            return
        }
        viewModelScope.launch{
            try{
                Log.d("EatSyncFirebase", "toggleReady called for $userName")
                firebaseSessionManager.toggleReady(
                    sessionCode = sessionCode,
                    participant = participant
                )
                Log.d("EatSyncFirebase", "Ready status updated")
            }catch(e: Exception){
                Log.e("EatSyncFirebase", "Error toggling ready", e)
            }
        }
    }
}