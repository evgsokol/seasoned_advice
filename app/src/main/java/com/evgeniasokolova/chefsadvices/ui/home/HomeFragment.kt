package com.evgeniasokolova.chefsadvices.ui.home

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.evgeniasokolova.chefsadvices.R
import com.evgeniasokolova.chefsadvices.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var adapter: QuestionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = QuestionsAdapter { question ->
            findNavController().navigate(HomeFragmentDirections.goToDetail(
                question.question_id,
                question.title,
                question.body.hideBlockElements(),
                question.isFavorite)
            )
        }

        binding.questionsList.adapter = adapter

        homeViewModel.loadQuestions()

        binding.swipeToRefresh.setOnRefreshListener {
            homeViewModel.loadQuestions()
        }

        observeUi()

    }

    private fun observeUi() {
        homeViewModel.viewEvent.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeUiEvent.ShowData -> {
                    adapter.submitList(state.data.items)
                }
                is HomeUiEvent.ShowLoading -> binding.progressBar.visibility = View.VISIBLE
                is HomeUiEvent.HideLoading -> binding.progressBar.visibility = View.GONE
                is HomeUiEvent.HideRefreshLayout -> binding.swipeToRefresh.isRefreshing = false
                is HomeUiEvent.ShowError -> {
                    Toast.makeText(requireContext(),
                        getString(R.string.internet_connection_message),
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun String.hideBlockElements(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            @Suppress("DEPRECATION")
            return Html.fromHtml(this).toString()
        }
    }
}
