package com.example.gymcoach.domain.model

data class MessageModel(
    val id: String = "",
    val senderId: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
