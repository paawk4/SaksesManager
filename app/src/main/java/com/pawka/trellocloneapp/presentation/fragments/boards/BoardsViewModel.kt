package com.pawka.trellocloneapp.presentation.fragments.boards

import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.domain.board.use_cases.GetBoardsListUseCase

class BoardsViewModel(private val getBoardsList: GetBoardsListUseCase) : ViewModel() {

    val boardsListLiveData = getBoardsList.getBoardsList()
}