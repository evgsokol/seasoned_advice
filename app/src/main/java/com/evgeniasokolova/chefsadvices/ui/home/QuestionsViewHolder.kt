package com.evgeniasokolova.chefsadvices.ui.home

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.evgeniasokolova.chefsadvices.data.api.Question
import com.evgeniasokolova.chefsadvices.databinding.QuestionItemBinding
import timber.log.Timber

class QuestionsViewHolder(
    private val itemBinding: QuestionItemBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(question: Question) {
        itemBinding.title.text = question.title
        itemBinding.answerNum.text = question.answer_count.toString()
        itemBinding.viewsNum.text = question.view_count.toString()
        itemBinding.voteNum.text = question.score.toString()
        itemBinding.ownerImage.load(question.owner.profile_image) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        itemBinding.ownerName.text = question.owner.display_name
    }
}