package com.pawka.trellocloneapp.domain.board.use_cases

import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.BoardRepository

class CreateBoardUseCase(private val boardRepository: BoardRepository) {

    fun createBoard(board: Board) {
        return boardRepository.createBoard(board)
    }
}