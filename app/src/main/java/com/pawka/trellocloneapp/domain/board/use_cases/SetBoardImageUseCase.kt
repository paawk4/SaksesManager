package com.pawka.trellocloneapp.domain.board.use_cases

import android.net.Uri
import com.pawka.trellocloneapp.domain.board.BoardRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SetBoardImageUseCase : KoinComponent {
    private val boardRepository: BoardRepository by inject()
    fun setBoardImage(uri: Uri, callback: () -> Unit) {
        return boardRepository.setBoardImage(uri, callback)
    }
}