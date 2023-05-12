package com.pawka.trellocloneapp.domain.board.use_cases

import android.net.Uri
import com.pawka.trellocloneapp.domain.board.BoardRepository

class SetBoardImageUseCase(private val repository: BoardRepository) {

    fun setBoardImage(uri: Uri, callback: () -> Unit) {
        return repository.setBoardImage(uri, callback)
    }
}