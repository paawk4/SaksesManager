package com.pawka.trellocloneapp.domain.board.use_cases

import com.pawka.trellocloneapp.domain.board.BoardRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteBoardUseCase : KoinComponent {
    private val boardRepository: BoardRepository by inject()
    fun deleteBoard(callback: () -> Unit) {
        return boardRepository.deleteBoard(callback)
    }
}