package com.pawka.trellocloneapp.presentation.fragments.task_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.use_cases.AddUpdateTaskListUseCase
import com.pawka.trellocloneapp.domain.board.use_cases.DeleteBoardUseCase
import com.pawka.trellocloneapp.domain.board.use_cases.GetBoardDetailsUseCase
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.use_cases.GetAssignedMembersListUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentUserIdUseCase

class TaskListViewModel(
    private val getBoardDetailsUseCase: GetBoardDetailsUseCase,
    private val addUpdateTaskListUseCase: AddUpdateTaskListUseCase,
    private val deleteBoardUseCase: DeleteBoardUseCase,
    private val getAssignedMembersListUseCase: GetAssignedMembersListUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    var assignedMembersListLiveData = MutableLiveData<ArrayList<User>>()
    var currentBoardLiveData = MutableLiveData<Board>()

    fun getBoardsDetails(boardId: String, callback: (Board) -> Unit) {
        currentBoardLiveData = getBoardDetailsUseCase.getBoardsDetails(boardId, callback)
    }

    fun getAssignedMembersList(assignedTo: ArrayList<String>, callback: () -> Unit) {
        assignedMembersListLiveData =
            getAssignedMembersListUseCase.getAssignedMembersList(assignedTo, callback)
    }

    fun addUpdateTaskList(board: Board, callback: (Boolean) -> Unit) {
        addUpdateTaskListUseCase.addUpdateTaskList(board, callback)
    }

    fun getCurrentUserId(): String? {
        return getCurrentUserIdUseCase.getCurrentUserId()
    }

    fun deleteBoard(callback: () -> Unit) {
        deleteBoardUseCase.deleteBoard(callback)
    }
}