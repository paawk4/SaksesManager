package com.pawka.trellocloneapp.domain.board

interface BoardRepository {

    fun createBoard(board: Board)

    fun getBoardDetails(boardId: String)

    fun getBoardsList()

    fun deleteBoard()

    fun changeBoardDetails()
}