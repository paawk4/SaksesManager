package com.pawka.trellocloneapp.presentation.fragments.boards

import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.data.BoardRepositoryImpl
import com.pawka.trellocloneapp.domain.board.use_cases.GetBoardsListUseCase

class BoardsViewModel: ViewModel() {

    private val repository = BoardRepositoryImpl

    private val getBoardsListLiveDataUseCase = GetBoardsListUseCase(repository)

    val boardsListLiveData = getBoardsListLiveDataUseCase.getBoardsList()
}