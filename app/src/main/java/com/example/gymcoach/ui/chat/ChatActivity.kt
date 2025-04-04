package com.example.gymcoach.ui.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymcoach.auth.FakeAuth
import com.example.gymcoach.databinding.ActivityChatBinding
import com.example.gymcoach.ui.chat.adapter.ChatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatAdapter
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var chatId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val senderId = FakeAuth.currentUserId
        val receiverId: String by lazy {
            intent.getStringExtra("receiverId") ?: "default_chat"
        }
        chatId = listOf(senderId, receiverId).sorted().joinToString("_")
        viewModel.loadMessages(chatId)

        binding.rvMsg.layoutManager = LinearLayoutManager(this)
        binding.tvTitle.text = "Chat con $receiverId"
        lifecycleScope.launch {
            viewModel.messages.collect { messages ->
                adapter = ChatAdapter(messages)
                binding.rvMsg.adapter = adapter
                binding.rvMsg.scrollToPosition(messages.size - 1)
            }
        }

        binding.btnSendMsg.setOnClickListener {
            val text = binding.etChat.text.toString()
            if (text.isNotBlank()) {
                viewModel.sendMessage(chatId, text)
                binding.etChat.text.clear()
            }
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}
