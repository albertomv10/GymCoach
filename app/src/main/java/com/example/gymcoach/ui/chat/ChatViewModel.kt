
package com.example.gymcoach.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymcoach.auth.FakeAuth
import com.example.gymcoach.domain.GetMessagesUseCase
import com.example.gymcoach.domain.SendMessageUseCase
import com.example.gymcoach.domain.model.MessageModel
//import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    val messages: StateFlow<List<MessageModel>> get() = _messages

    fun loadMessages(chatId: String) {
        viewModelScope.launch {
            getMessagesUseCase(chatId).collectLatest {
                _messages.value = it
            }
        }
    }

    fun sendMessage(chatId: String, text: String) {
        val senderId = FakeAuth.currentUserId //FirebaseAuth.getInstance().currentUser?.uid ?: return
        val message = MessageModel(
            senderId = senderId,
            text = text
        )
        sendMessageUseCase(chatId, message)
    }
}
