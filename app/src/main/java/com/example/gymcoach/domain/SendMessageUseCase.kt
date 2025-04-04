
package com.example.gymcoach.domain

import com.example.gymcoach.domain.model.MessageModel
import com.example.gymcoach.data.network.FirebaseChatService
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val firebaseChatService: FirebaseChatService
) {
    operator fun invoke(chatId: String, message: MessageModel) {
        firebaseChatService.sendMessage(chatId, message)
    }
}
