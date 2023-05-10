package com.pawka.trellocloneapp.domain.board.use_cases

import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.BoardRepository

class AddUpdateTaskListUseCase(private val boardRepository: BoardRepository) {

    fun addUpdateTaskList(board: Board, callback: (Boolean) -> Unit) {
        return boardRepository.addUpdateTaskList(board, callback)
    }
}