package com.evgeniasokolova.chefsadvices.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.evgeniasokolova.chefsadvices.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val params by navArgs<DetailFragmentArgs>()

    private lateinit var binding: FragmentDetailBinding

    private val detailViewModel: DetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.getQuestion(params.questionId)
        observeDetail()
    }

    private fun observeDetail() {
        binding.title.text = params.title
        binding.body.text = params.text
    }

}