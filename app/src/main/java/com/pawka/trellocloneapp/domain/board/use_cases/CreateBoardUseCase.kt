package com.pawka.trellocloneapp.domain.board.use_cases

import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.BoardRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreateBoardUseCase : KoinComponent {
    private val boardRepository: BoardRepository by inject()
    fun createBoard(board: Board, callback: () -> Unit) {
        return boardRepository.createBoard(board, callback)
    }
}