package com.pawka.trellocloneapp.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.pawka.trellocloneapp.data.UserRepositoryImpl.getCurrentUserId
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.BoardRepository
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.utils.APP_ACTIVITY

object BoardRepositoryImpl : BoardRepository {

    private val boardsListLiveData = MutableLiveData<ArrayList<Board>>()

    private const val BOARDS = "boards"
    private const val ASSIGNED_TO: String = "assignedTo"

    private val boardFireStoreHandler = BoardFireStoreHandler()

    override fun createBoard(board: Board, callback: () -> Unit) {
        boardFireStoreHandler.createBoard(board, callback)
    }

    override fun getBoardDetails(boardId: String, callback: (Board) -> Unit) {
        boardFireStoreHandler.getBoardDetails(boardId, callback)
    }

    override fun getBoardsList(): MutableLiveData<ArrayList<Board>> {
        boardFireStoreHandler.getBoardsList()
        return boardsListLiveData
    }

    override fun deleteBoard() {
        TODO("Not yet implemented")
    }

    override fun changeBoardDetails() {
        TODO("Not yet implemented")
    }

    override fun assignMemberToBoard(board: Board, callback: () -> Unit) {
        boardFireStoreHandler.assignMemberToBoard(board, callback)
    }

    class BoardFireStoreHandler {

        private val mFireStore = FirebaseFirestore.getInstance()

        fun createBoard(boardInfo: Board, callback: () -> Unit) {
            mFireStore.collection(BOARDS)
                .document()
                .set(boardInfo, SetOptions.merge())
                .addOnSuccessListener {
                    getBoardsList()
                    callback()
                }.addOnFailureListener {
                    Log.d("Boards", "create board failed ${it.message}")
                }
        }

        fun getBoardsList() {
            val currentUserId = getCurrentUserId()
            currentUserId?.let { id ->
                mFireStore.collection(BOARDS)
                    .whereArrayContains(ASSIGNED_TO, id)
                    .get()
                    .addOnSuccessListener { document ->
                        Log.e("GetBoardList", document.documents.toString())
                        val boardsList: ArrayList<Board> = ArrayList()
                        for (i in document.documents) {
                            val board = i.toObject(Board::class.java)!!
                            board.documentID = i.id
                            boardsList.add(board);
                        }
                        boardsListLiveData.value = boardsList
                    }
            }

        }

        fun getBoardDetails(boardId: String, callback: (Board) -> Unit) {
            mFireStore.collection(BOARDS)
                .document(boardId)
                .get()
                .addOnSuccessListener { document ->
                    Log.e("getBoardDetails", document.toString())
                    val board = document.toObject(Board::class.java)!!
                    board.documentID = document.id
                    callback(board)
                }
        }

        fun assignMemberToBoard(board: Board, callback: () -> Unit) {
            val assignedToHashMap = HashMap<String, Any>()
            assignedToHashMap[ASSIGNED_TO] = board.assignedTo

            mFireStore.collection(BOARDS)
                .document(board.documentID)
                .update(assignedToHashMap)
                .addOnSuccessListener {
                    callback()
                }
                .addOnFailureListener { e ->
                    Log.e(APP_ACTIVITY.javaClass.simpleName, "Error while creating a board", e)
                }
        }
    }
}