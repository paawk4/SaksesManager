package com.pawka.trellocloneapp.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.BoardRepository

object BoardRepositoryImpl: BoardRepository {

    private val boardsListLiveData = MutableLiveData<ArrayList<Board>>()
    private const val BOARDS = "boards"
    private val boardFireStoreHandler = BoardFireStoreHandler()

    override fun createBoard(board: Board, callback: () -> Unit) {
        boardFireStoreHandler.createBoard(board, callback)
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

    class BoardFireStoreHandler {

        private val mFireStore = FirebaseFirestore.getInstance()

        fun createBoard(boardInfo : Board, callback: () -> Unit){
            mFireStore.collection(BOARDS)
                .document()
                .set(boardInfo, SetOptions.merge())
                .addOnSuccessListener {
                    callback()
                }.addOnFailureListener {
                    Log.d("Boards", "create board failed ${it.message}")
                }
        }
    }
}