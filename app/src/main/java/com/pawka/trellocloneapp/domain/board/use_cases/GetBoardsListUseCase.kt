package com.pawka.trellocloneapp.domain.board.use_cases

import androidx.lifecycle.MutableLiveData
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.BoardRepository

class GetBoardsListUseCase(private val boardRepository: BoardRepository) {

    fun getBoardsList(): MutableLiveData<ArrayList<Board>> {
        return boardRepository.getBoardsList()
    }
}