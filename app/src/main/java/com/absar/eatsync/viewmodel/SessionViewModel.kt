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
import com.absar.eatsync.model.SelectedRestaurant
import com.absar.eatsync.model.EatSyncSession

class SessionViewModel : ViewModel() {

    private val firebaseSessionManager = FirebaseSessionManager()

    private val _participants = MutableStateFlow<List<Participant>>(emptyList())
    val participants: StateFlow<List<Participant>> = _participants.asStateFlow()
    private val _selectedRestaurant = MutableStateFlow<SelectedRestaurant?>(null)
    val selectedRestaurant: StateFlow<SelectedRestaurant?> = _selectedRestaurant.asStateFlow()
    private val _sessionInfo = MutableStateFlow<EatSyncSession?>(null)
    val sessionInfo: StateFlow<EatSyncSession?> = _sessionInfo.asStateFlow()

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
                observeSelectedRestaurant(sessionCode)
                observeSessionInfo(sessionCode)

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
                observeSelectedRestaurant(sessionCode)
                observeSessionInfo(sessionCode)
            }catch(e: Exception){
                Log.e("EatSyncFirebase", "Error joining Firebase session", e)
            }
        }
    }

    fun updateSelectedRestaurant(
        restaurantId: String,
        restaurantName: String
    ) {
        val sessionCode = _currentSessionCode.value
        if(sessionCode == null){
            Log.e("EatSyncFirebase", "updateSelectedRestaurant failed: sessionCode is null")
            return
        }
        if(_sessionInfo.value?.cartLocked == true){
            Log.d("EatSyncFirebase", "updateSelectedRestaurant blocked: cart is locked")
            return
        }
        val existingRestaurant=_selectedRestaurant.value
        if(existingRestaurant != null && existingRestaurant.id.isNotEmpty()){
            Log.d("EatSyncFirebase", "updateSelectedRestaurant blocked: restaurant already selected")
            return
        }
        viewModelScope.launch{
            try{
                firebaseSessionManager.updateSelectedRestaurant(
                    sessionCode = sessionCode,
                    restaurantId = restaurantId,
                    restaurantName = restaurantName
                )
                Log.d("EatSyncFirebase", "Restaurant selection saved from ViewModel")
            }
            catch(e: Exception){
                Log.e("EatSyncFirebase", "Error updating selected restaurant", e)
            }
        }
    }

    fun clearRestaurantAndCart(){
        val sessionCode = _currentSessionCode.value
        if(sessionCode==null){
            Log.e("EatSyncFirebase", "clearRestaurantAndCart failed: sessionCode is null")
            return
        }
        viewModelScope.launch{
            try{
                firebaseSessionManager.clearRestaurantAndCart(sessionCode)
                Log.d("EatSyncFirebase", "Restaurant and cart cleared from ViewModel")
            }
            catch(e: Exception){
                Log.e("EatSyncFirebase", "Error clearing restaurant and cart", e)
            }
        }
    }

    private fun observeSessionInfo(sessionCode: String){
        viewModelScope.launch{
            try {
                Log.d("EatSyncFirebase", "observeSessionInfo started for $sessionCode")
                firebaseSessionManager.observeSessionInfo(sessionCode)
                    .collect{session->
                        _sessionInfo.value=session
                        Log.d(
                            "EatSyncFirebase",
                            "Session info updated in ViewModel: ${session?.status}"
                        )
                    }
            }
            catch(e: Exception){
                Log.e("EatSyncFirebase", "Error observing session info", e)
            }
        }
    }

    private fun observeSelectedRestaurant(sessionCode: String){
        viewModelScope.launch{
            try{
                Log.d("EatSyncFirebase", "observeSelectedRestaurant started for $sessionCode")
                firebaseSessionManager.observeSelectedRestaurant(sessionCode)
                    .collect { restaurant ->
                        _selectedRestaurant.value = restaurant
                        Log.d(
                            "EatSyncFirebase",
                            "Selected restaurant updated in ViewModel: ${restaurant?.name}"
                        )
                    }
            }
            catch (e: Exception){
                Log.e("EatSyncFirebase", "Error observing selected restaurant", e)
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

    fun lockCart(){
        val sessionCode = _currentSessionCode.value
        if(sessionCode==null){
            Log.e("EatSyncFirebase", "lockCart failed: sessionCode is null")
            return
        }
        viewModelScope.launch{
            try{
                firebaseSessionManager.lockCart(sessionCode)
                Log.d("EatSyncFirebase", "Cart locked from ViewModel")
            }
            catch(e: Exception){
                Log.e("EatSyncFirebase", "Error locking cart", e)
            }
        }
    }

    fun unlockCart(){
        val sessionCode = _currentSessionCode.value
        if(sessionCode==null){
            Log.e("EatSyncFirebase", "unlockCart failed: sessionCode is null")
            return
        }
        viewModelScope.launch{
            try{
                firebaseSessionManager.unlockCart(sessionCode)
                Log.d("EatSyncFirebase", "Cart unlocked from ViewModel")
            }
            catch(e: Exception){
                Log.e("EatSyncFirebase", "Error unlocking cart", e)
            }
        }
    }

    fun toggleReady(userName: String){
        val sessionCode= _currentSessionCode.value
        if(sessionCode==null){
            Log.e("EatSyncFirebase", "toggleReady failed: sessionCode is null")
            return
        }
        if(_sessionInfo.value?.cartLocked == true){
            Log.d("EatSyncFirebase", "toggleReady blocked: cart is locked")
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