package com.pawka.trellocloneapp.domain.board

import androidx.lifecycle.MutableLiveData

interface BoardRepository {

    fun createBoard(board: Board)

    fun getBoardDetails(boardId: String)

    fun getBoardsList(): MutableLiveData<ArrayList<Board>>

    fun deleteBoard()

    fun changeBoardDetails()
}