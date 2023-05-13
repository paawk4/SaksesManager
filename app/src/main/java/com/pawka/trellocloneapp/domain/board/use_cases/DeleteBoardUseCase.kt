package com.pawka.trellocloneapp.domain.board.use_cases

import com.pawka.trellocloneapp.domain.board.BoardRepository

class DeleteBoardUseCase(private val repository: BoardRepository) {

    fun deleteBoard(callback: () -> Unit) {
        return repository.deleteBoard(callback)
    }
}