
package com.example.gymcoach.domain

import com.example.gymcoach.domain.model.MessageModel
import com.example.gymcoach.data.network.FirebaseChatService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val firebaseChatService: FirebaseChatService
) {
    operator fun invoke(chatId: String): Flow<List<MessageModel>> {
        return firebaseChatService.getMessages(chatId)
    }
}
