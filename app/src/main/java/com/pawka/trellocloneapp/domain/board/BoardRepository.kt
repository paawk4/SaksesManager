package com.pawka.trellocloneapp.domain.board

import androidx.lifecycle.MutableLiveData
import com.pawka.trellocloneapp.domain.user.User

interface BoardRepository {

    fun createBoard(board: Board, callback: () -> Unit)

    fun getBoardDetails(boardId: String, callback: (Board) -> Unit): MutableLiveData<Board>

    fun getBoardsList(): MutableLiveData<ArrayList<Board>>

    fun deleteBoard()

    fun assignMemberToBoard(board: Board, callback: () -> Unit)

    fun addUpdateTaskList(board: Board, callback: (Boolean) -> Unit)
}