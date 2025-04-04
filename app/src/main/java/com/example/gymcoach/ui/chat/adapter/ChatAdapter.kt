
package com.example.gymcoach.ui.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymcoach.R
import com.example.gymcoach.auth.FakeAuth
import com.example.gymcoach.domain.model.MessageModel
//import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(private val messages: List<MessageModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val currentUserId = FakeAuth.currentUserId//FirebaseAuth.getInstance().currentUser?.uid

    companion object {
        private const val TYPE_SENT = 1
        private const val TYPE_RECEIVED = 2
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.senderId == currentUserId) TYPE_SENT else TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_SENT) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_sent, parent, false)
            SentMessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_received, parent, false)
            ReceivedMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder is SentMessageViewHolder) {
            holder.bind(message)
        } else if (holder is ReceivedMessageViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtMessage: TextView = itemView.findViewById(R.id.tvMessageSent)
        fun bind(message: MessageModel) {
            txtMessage.text = message.text
        }
    }

    class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtMessage: TextView = itemView.findViewById(R.id.tvMessageReceived)
        fun bind(message: MessageModel) {
            txtMessage.text = message.text
        }
    }
}
