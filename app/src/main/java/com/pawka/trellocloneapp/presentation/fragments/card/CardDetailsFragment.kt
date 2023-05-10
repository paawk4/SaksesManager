package com.pawka.trellocloneapp.presentation.fragments.card

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.presentation.fragments.BaseFragment

class CardDetailsFragment : BaseFragment(R.layout.fragment_card_details) {

    private lateinit var viewModel: CardDetailsViewModel
    private var currentBoard = Board()
    private var taskListPosition = -1
    private var cardListPosition = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CardDetailsViewModel::class.java]
        getReceivedArguments()
    }

    private fun getReceivedArguments() {
        val boardId = arguments?.getString("boardId")
        boardId?.let { id ->
            viewModel.getBoardsDetails(id) {
                currentBoard = it
            }
        }
        taskListPosition = arguments?.getInt("taskListItemPosition")!!
        cardListPosition = arguments?.getInt("cardListItemPosition")!!
    }
}