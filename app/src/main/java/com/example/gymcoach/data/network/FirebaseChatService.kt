
package com.example.gymcoach.data.network

import com.example.gymcoach.domain.model.MessageModel
import com.google.firebase.database.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseChatService @Inject constructor() {

    private val database = FirebaseDatabase.getInstance().reference

    fun getMessages(chatId: String): Flow<List<MessageModel>> = callbackFlow {
        val chatRef = database.child("chats").child(chatId)

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = snapshot.children.mapNotNull { it.getValue(MessageModel::class.java) }
                trySend(messages)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        chatRef.addValueEventListener(listener)
        awaitClose { chatRef.removeEventListener(listener) }
    }

    fun sendMessage(chatId: String, message: MessageModel) {
        val chatRef = database.child("chats").child(chatId)
        val newMessageRef = chatRef.push()
        val messageWithId = message.copy(id = newMessageRef.key ?: "")
        newMessageRef.setValue(messageWithId)
    }
}
