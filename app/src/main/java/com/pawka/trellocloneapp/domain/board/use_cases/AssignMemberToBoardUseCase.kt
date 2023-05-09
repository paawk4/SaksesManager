package com.pawka.trellocloneapp.domain.board.use_cases

import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.BoardRepository
import com.pawka.trellocloneapp.domain.user.User

class AssignMemberToBoardUseCase(private val boardRepository: BoardRepository) {

    fun assignMemberToBoard(board: Board, callback: () -> Unit) {
        return boardRepository.assignMemberToBoard(board, callback)
    }
}
