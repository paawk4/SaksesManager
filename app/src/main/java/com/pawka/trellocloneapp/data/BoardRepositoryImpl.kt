package com.pawka.trellocloneapp.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.BoardRepository

object BoardRepositoryImpl: BoardRepository {

    private val boardsListLiveData = MutableLiveData<ArrayList<Board>>()
    private const val BOARDS = "boards"

    private val db = FirebaseDatabase.getInstance(UserRepositoryImpl.DB_URL).reference.child(BOARDS)

    override fun createBoard(board: Board) {
        db.child(board.name).setValue(board)
            .addOnFailureListener {
                Log.d("Register", "writeUserToDatabase failed\n ${it.message}")
            }
    }

    override fun getBoardDetails(boardId: String) {
        TODO("Not yet implemented")
    }

    override fun getBoardsList(): MutableLiveData<ArrayList<Board>> {
        return boardsListLiveData
    }

    override fun deleteBoard() {
        TODO("Not yet implemented")
    }

    override fun changeBoardDetails() {
        TODO("Not yet implemented")
    }
}