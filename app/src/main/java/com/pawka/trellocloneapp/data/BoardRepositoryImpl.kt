package com.pawka.trellocloneapp.data

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.pawka.trellocloneapp.data.UserRepositoryImpl.getCurrentUserId
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.BoardRepository
import com.pawka.trellocloneapp.utils.APP_ACTIVITY
import kotlin.random.Random

object BoardRepositoryImpl : BoardRepository {

    private val boardsListLiveData = MutableLiveData<ArrayList<Board>>()
    private val currentBoardLiveData = MutableLiveData<Board>()
    private var imageUrl = ""

    private const val BOARDS = "boards"
    private const val ASSIGNED_TO: String = "assignedTo"
    private const val TASK_LIST: String = "taskList"

    private val boardFireStoreHandler = BoardFireStoreHandler()
    private val storage = FirebaseStorage.getInstance().reference

    override fun createBoard(board: Board, callback: () -> Unit) {
        boardFireStoreHandler.createBoard(board, callback)
    }

    override fun getBoardDetails(
        boardId: String,
        callback: (Board) -> Unit
    ): MutableLiveData<Board> {
        boardFireStoreHandler.getBoardDetails(boardId, callback)
        return currentBoardLiveData
    }

    override fun getBoardsList(): MutableLiveData<ArrayList<Board>> {
        boardFireStoreHandler.getBoardsList()
        return boardsListLiveData
    }

    override fun deleteBoard(callback: () -> Unit) {
        boardFireStoreHandler.deleteBoard(callback)
    }

    override fun setBoardImage(uri: Uri, callback: () -> Unit) {
        storage.child("board_image").child(Random.nextBytes(10).toString()).putFile(uri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { url ->
                    Log.e("Downloadable Image URL", uri.toString())
                    imageUrl = url.toString()
                    callback()
                }
            }

    }

    override fun assignMemberToBoard(board: Board, callback: () -> Unit) {
        boardFireStoreHandler.assignMemberToBoard(board, callback)
    }

    override fun addUpdateTaskList(board: Board, callback: (Boolean) -> Unit) {
        boardFireStoreHandler.addUpdateTaskList(board, callback)
    }

    class BoardFireStoreHandler {

        private val db = FirebaseFirestore.getInstance().collection(BOARDS)

        fun createBoard(boardInfo: Board, callback: () -> Unit) {
            boardInfo.image = imageUrl
            db.document()
                .set(boardInfo, SetOptions.merge())
                .addOnSuccessListener {
                    getBoardsList()
                    callback()
                }.addOnFailureListener {
                    Log.d("Boards", "create board failed ${it.message}")
                }
        }

        fun deleteBoard(callback: () -> Unit) {
            db.document(currentBoardLiveData.value?.documentID!!)
                .delete()
                .addOnSuccessListener {
                    getBoardsList()
                    callback()
                }
        }

        fun getBoardsList() {
            val currentUserId = getCurrentUserId()
            currentUserId?.let { id ->
                db.whereArrayContains(ASSIGNED_TO, id)
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
            db.document(boardId)
                .get()
                .addOnSuccessListener { document ->
                    Log.e("getBoardDetails", document.toString())
                    val board = document.toObject(Board::class.java)!!
                    board.documentID = document.id
                    currentBoardLiveData.value = board
                    callback(board)
                }
        }

        fun assignMemberToBoard(board: Board, callback: () -> Unit) {
            val assignedToHashMap = HashMap<String, Any>()
            assignedToHashMap[ASSIGNED_TO] = board.assignedTo

            db.document(board.documentID)
                .update(assignedToHashMap)
                .addOnSuccessListener {
                    currentBoardLiveData.value = board
                    callback()
                }
                .addOnFailureListener { e ->
                    Log.e(APP_ACTIVITY.javaClass.simpleName, "Error while creating a board", e)
                }
        }

        fun addUpdateTaskList(board: Board, callback: (Boolean) -> Unit) {
            val taskListHashMap = HashMap<String, Any>()
            taskListHashMap[TASK_LIST] = board.taskList

            db.document(board.documentID)
                .update(taskListHashMap)
                .addOnSuccessListener {
                    currentBoardLiveData.value = board
                    Log.e(APP_ACTIVITY.javaClass.simpleName, "TaskList updated successfully.")
                    callback(true)
                }
                .addOnFailureListener { e ->
                    callback(false)
                    Log.e(APP_ACTIVITY.javaClass.simpleName, "Error while creating a board.", e)
                }
        }
    }
}