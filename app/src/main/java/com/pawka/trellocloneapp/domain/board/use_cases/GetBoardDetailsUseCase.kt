package com.pawka.trellocloneapp.domain.board.use_cases

import androidx.lifecycle.MutableLiveData
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.BoardRepository
import com.pawka.trellocloneapp.domain.user.User

class GetBoardDetailsUseCase(private val repository: BoardRepository) {

    fun getBoardsDetails(boardId: String, callback: (Board) -> Unit): MutableLiveData<Board> {
        return repository.getBoardDetails(boardId, callback)
    }
}