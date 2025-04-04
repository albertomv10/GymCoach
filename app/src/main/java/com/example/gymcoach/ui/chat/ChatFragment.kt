
package com.example.gymcoach.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymcoach.auth.FakeAuth
import com.example.gymcoach.databinding.FragmentChatBinding
import com.example.gymcoach.ui.chat.adapter.ChatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()
    //private val args by navArgs<ChatFragmentArgs>()

    private lateinit var adapter: ChatAdapter
    private lateinit var chatId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val receiverId = arguments?.getString("receiverId") ?: return
        //val receiverId = args.receiverId
        val senderId = FakeAuth.currentUserId //FirebaseAuth.getInstance().currentUser?.uid ?: return
        chatId = listOf(senderId, receiverId).sorted().joinToString("_")

        adapter = ChatAdapter(emptyList())
        binding.rvMsg.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMsg.adapter = adapter

        binding.tvTitle.text = "Chat con $receiverId" // Ideal: mostrar nombre real

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSendMsg.setOnClickListener {
            val msg = binding.etChat.text.toString()
            if (msg.isNotBlank()) {
                viewModel.sendMessage(chatId, msg)
                binding.etChat.setText("")
            }
        }

        viewModel.loadMessages(chatId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.messages.collect { messages ->
                adapter = ChatAdapter(messages)
                binding.rvMsg.adapter = adapter
                binding.rvMsg.scrollToPosition(messages.size - 1)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
