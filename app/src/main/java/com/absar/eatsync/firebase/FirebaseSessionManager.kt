package com.absar.eatsync.firebase

import android.util.Log
import com.absar.eatsync.model.Participant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseSessionManager{
    private val database=FirebaseDatabase
        .getInstance("https://eatsync-76bba-default-rtdb.asia-southeast1.firebasedatabase.app")
        .reference

    suspend fun createSession(
        sessionCode: String,
        hostName: String
    ){
        Log.d("EatSyncFirebase", "FirebaseSessionManager createSession started")
        val hostParticipant=Participant(
            userId = "host_$sessionCode",
            name = hostName,
            host = true,
            ready = false
        )
        val sessionData=mapOf(
            "sessionCode" to sessionCode,
            "hostName" to hostName,
            "status" to "WAITING",
            "createdAt" to System.currentTimeMillis()
        )
        database.child("sessions")
            .child(sessionCode)
            .setValue(sessionData)
            .await()
        Log.d("EatSyncFirebase", "Session basic data written")
        database.child("sessions")
            .child(sessionCode)
            .child("participants")
            .child(hostParticipant.userId)
            .setValue(hostParticipant)
            .await()
        Log.d("EatSyncFirebase", "Host participant written")
    }

    suspend fun joinSession(
        sessionCode: String,
        userName: String
    ){
        Log.d("EatSyncFirebase", "FirebaseSessionManager joinSession started")
        val userId = "user_${System.currentTimeMillis()}"
        val participant = Participant(
            userId = userId,
            name = userName,
            host = false,
            ready = false
        )
        database.child("sessions")
            .child(sessionCode)
            .child("participants")
            .child(userId)
            .setValue(participant)
            .await()
        Log.d("EatSyncFirebase", "Joined participant written")
    }

    fun observeParticipants(sessionCode: String): Flow<List<Participant>> = callbackFlow{
        val participantsRef = database.child("sessions")
            .child(sessionCode)
            .child("participants")
        val listener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                val participants = snapshot.children.mapNotNull{child->
                    child.getValue(Participant::class.java)
                }
                Log.d("EatSyncFirebase", "observeParticipants received ${participants.size} participants")
                trySend(participants)
            }
            override fun onCancelled(error: DatabaseError){
                Log.e("EatSyncFirebase", "observeParticipants cancelled", error.toException())
                close(error.toException())
            }
        }
        participantsRef.addValueEventListener(listener)
        awaitClose{
            participantsRef.removeEventListener(listener)
        }
    }

    suspend fun toggleReady(
        sessionCode: String,
        participant: Participant
    ){
        database.child("sessions")
            .child(sessionCode)
            .child("participants")
            .child(participant.userId)
            .child("ready")
            .setValue(!participant.ready)
            .await()
        Log.d("EatSyncFirebase", "Ready toggled for ${participant.name}")
    }
}