package com.pawka.trellocloneapp.domain.board.use_cases

import androidx.lifecycle.MutableLiveData
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.BoardRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetBoardsListUseCase : KoinComponent {
    private val boardRepository: BoardRepository by inject()
    fun getBoardsList(): MutableLiveData<ArrayList<Board>> {
        return boardRepository.getBoardsList()
    }
}