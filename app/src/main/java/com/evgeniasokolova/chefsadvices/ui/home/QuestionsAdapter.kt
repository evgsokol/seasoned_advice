package com.evgeniasokolova.chefsadvices.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.evgeniasokolova.chefsadvices.data.api.Question
import com.evgeniasokolova.chefsadvices.databinding.QuestionItemBinding
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class QuestionsAdapter @Inject constructor(private val callback: (Question) -> Unit) :
    ListAdapter<Question, QuestionsViewHolder>(QuestionsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = QuestionItemBinding.inflate(layoutInflater, parent, false)
        return QuestionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        val question = getItem(position)
        holder.itemView.setOnClickListener {
            callback.invoke(question)
            notifyItemChanged(position)
        }
        holder.bind(question)
    }
}


class QuestionsDiffCallback : DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(oldItem: Question, newItem: Question) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Question, newItem: Question) =
        oldItem == newItem
}
