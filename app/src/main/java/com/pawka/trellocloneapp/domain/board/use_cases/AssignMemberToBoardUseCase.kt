package com.pawka.trellocloneapp.domain.board.use_cases

import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.BoardRepository
import com.pawka.trellocloneapp.domain.user.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AssignMemberToBoardUseCase : KoinComponent {
    private val boardRepository: BoardRepository by inject()
    fun assignMemberToBoard(board: Board, callback: () -> Unit) {
        return boardRepository.assignMemberToBoard(board, callback)
    }
}
