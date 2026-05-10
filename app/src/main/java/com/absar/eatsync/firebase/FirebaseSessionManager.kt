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
import com.absar.eatsync.model.CartItem
import com.absar.eatsync.model.SelectedRestaurant
import com.absar.eatsync.model.EatSyncSession

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
            userId="host_$sessionCode",
            name=hostName,
            host=true,
            ready=false
        )
        val sessionData = mapOf(
            "sessionCode" to sessionCode,
            "hostName" to hostName,
            "status" to "WAITING",
            "cartLocked" to false,
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
            userId=userId,
            name=userName,
            host=false,
            ready=false
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
        val participantsRef=database.child("sessions")
            .child(sessionCode)
            .child("participants")
        val listener=object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                val participants=snapshot.children.mapNotNull{child->
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

    fun observeSessionInfo(sessionCode: String): Flow<EatSyncSession?> = callbackFlow {
        val sessionRef=database.child("sessions")
            .child(sessionCode)
        val listener=object:ValueEventListener{
            override fun onDataChange(snapshot:DataSnapshot){
                val session=snapshot.getValue(EatSyncSession::class.java)
                Log.d(
                    "EatSyncFirebase",
                    "observeSessionInfo received: ${session?.status}, locked=${session?.cartLocked}"
                )
                trySend(session)
            }
            override fun onCancelled(error:DatabaseError){
                Log.e("EatSyncFirebase", "observeSessionInfo cancelled", error.toException())
                close(error.toException())
            }
        }
        sessionRef.addValueEventListener(listener)
        awaitClose {
            sessionRef.removeEventListener(listener)
        }
    }
    suspend fun lockCart(sessionCode:String){
        val updates=mapOf<String,Any>(
            "cartLocked" to true,
            "status" to "CART_LOCKED"
        )
        database.child("sessions")
            .child(sessionCode)
            .updateChildren(updates)
            .await()
        Log.d("EatSyncFirebase", "Cart locked for session: $sessionCode")
    }
    suspend fun unlockCart(sessionCode: String) {
        val updates = mapOf<String, Any>(
            "cartLocked" to false,
            "status" to "RESTAURANT_SELECTED"
        )
        database.child("sessions")
            .child(sessionCode)
            .updateChildren(updates)
            .await()
        resetAllReadyStatus(sessionCode)
        Log.d("EatSyncFirebase","Cart unlocked for session: $sessionCode")
    }
    suspend fun clearRestaurantAndCart(sessionCode:String){
        val updates=mapOf<String, Any>(
            "cartLocked" to false,
            "status" to "WAITING"
        )
        database.child("sessions")
            .child(sessionCode)
            .updateChildren(updates)
            .await()
        database.child("sessions")
            .child(sessionCode)
            .child("selectedRestaurant")
            .removeValue()
            .await()
        database.child("sessions")
            .child(sessionCode)
            .child("cartItems")
            .removeValue()
            .await()
        resetAllReadyStatus(sessionCode)
        Log.d("EatSyncFirebase", "Restaurant cleared, cart cleared, ready reset")
    }

    private suspend fun resetAllReadyStatus(sessionCode: String){
        val participantsSnapshot=database.child("sessions")
            .child(sessionCode)
            .child("participants")
            .get()
            .await()
        participantsSnapshot.children.forEach{child->
            child.ref.child("ready")
                .setValue(false)
                .await()
        }
        Log.d("EatSyncFirebase", "All ready statuses reset")
    }

    suspend fun addOrUpdateCartItem(
        sessionCode:String,
        cartItem:CartItem
    ){
        database.child("sessions")
            .child(sessionCode)
            .child("cartItems")
            .child(cartItem.id)
            .setValue(cartItem)
            .await()
        Log.d("EatSyncFirebase", "Cart item added/updated: ${cartItem.name}")
    }

    fun observeCartItems(sessionCode: String): Flow<List<CartItem>> = callbackFlow {
        val cartRef=database.child("sessions")
            .child(sessionCode)
            .child("cartItems")
        val listener=object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                val items=snapshot.children.mapNotNull{child->
                    child.getValue(CartItem::class.java)
                }
                Log.d("EatSyncFirebase", "observeCartItems received ${items.size} items")
                trySend(items)
            }
            override fun onCancelled(error: DatabaseError){
                Log.e("EatSyncFirebase", "observeCartItems cancelled", error.toException())
                close(error.toException())
            }
        }
        cartRef.addValueEventListener(listener)
        awaitClose{
            cartRef.removeEventListener(listener)
        }
    }

    suspend fun removeCartItem(
        sessionCode:String,
        itemId:String
    ){
        database.child("sessions")
            .child(sessionCode)
            .child("cartItems")
            .child(itemId)
            .removeValue()
            .await()
        Log.d("EatSyncFirebase", "Cart item removed: $itemId")
    }

    suspend fun clearCart(sessionCode: String){
        database.child("sessions")
            .child(sessionCode)
            .child("cartItems")
            .removeValue()
            .await()
        Log.d("EatSyncFirebase", "Cart cleared")
    }

    suspend fun updateSelectedRestaurant(
        sessionCode:String,
        restaurantId:String,
        restaurantName:String
    ){
        val sessionSnapshot=database.child("sessions")
            .child(sessionCode)
            .get()
            .await()
        val session=sessionSnapshot.getValue(EatSyncSession::class.java)
        if(session?.cartLocked == true){
            Log.d("EatSyncFirebase", "Restaurant change blocked: cart is locked")
            return
        }
        val existingRestaurant=getSelectedRestaurant(sessionCode)
        if(existingRestaurant != null && existingRestaurant.id.isNotEmpty()){
            Log.d("EatSyncFirebase", "Restaurant change blocked: restaurant already selected")
            return
        }
        val restaurant=SelectedRestaurant(
            id=restaurantId,
            name=restaurantName
        )
        database.child("sessions")
            .child(sessionCode)
            .child("selectedRestaurant")
            .setValue(restaurant)
            .await()
        database.child("sessions")
            .child(sessionCode)
            .child("status")
            .setValue("RESTAURANT_SELECTED")
            .await()
        Log.d("EatSyncFirebase", "Selected restaurant updated: $restaurantName")
    }

    private suspend fun getSelectedRestaurant(sessionCode:String):SelectedRestaurant?{
        return database.child("sessions")
            .child(sessionCode)
            .child("selectedRestaurant")
            .get()
            .await()
            .getValue(SelectedRestaurant::class.java)
    }
    fun observeSelectedRestaurant(sessionCode: String): Flow<SelectedRestaurant?> = callbackFlow{
        val restaurantRef=database.child("sessions")
            .child(sessionCode)
            .child("selectedRestaurant")
        val listener=object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val restaurant=snapshot.getValue(SelectedRestaurant::class.java)
                Log.d(
                    "EatSyncFirebase",
                    "observeSelectedRestaurant received: ${restaurant?.name}"
                )
                trySend(restaurant)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("EatSyncFirebase", "observeSelectedRestaurant cancelled", error.toException())
                close(error.toException())
            }
        }
        restaurantRef.addValueEventListener(listener)
        awaitClose{
            restaurantRef.removeEventListener(listener)
        }
    }
    suspend fun toggleReady(
        sessionCode:String,
        participant:Participant
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