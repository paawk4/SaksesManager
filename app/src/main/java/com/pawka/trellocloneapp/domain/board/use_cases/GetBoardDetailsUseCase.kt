package com.pawka.trellocloneapp.domain.board.use_cases

import androidx.lifecycle.MutableLiveData
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.BoardRepository
import com.pawka.trellocloneapp.domain.user.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetBoardDetailsUseCase : KoinComponent {
    private val boardRepository: BoardRepository by inject()
    fun getBoardsDetails(boardId: String, callback: (Board) -> Unit): MutableLiveData<Board> {
        return boardRepository.getBoardDetails(boardId, callback)
    }
}