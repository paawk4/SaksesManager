package com.pawka.trellocloneapp.domain.board.use_cases

import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.BoardRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddUpdateTaskListUseCase : KoinComponent {
    private val boardRepository: BoardRepository by inject()
    fun addUpdateTaskList(board: Board, callback: (Boolean) -> Unit) {
        return boardRepository.addUpdateTaskList(board, callback)
    }
}