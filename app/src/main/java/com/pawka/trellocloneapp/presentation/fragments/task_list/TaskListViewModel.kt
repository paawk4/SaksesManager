package com.pawka.trellocloneapp.presentation.fragments.task_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.data.BoardRepositoryImpl
import com.pawka.trellocloneapp.data.UserRepositoryImpl
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.use_cases.AddUpdateTaskListUseCase
import com.pawka.trellocloneapp.domain.board.use_cases.GetBoardDetailsUseCase
import com.pawka.trellocloneapp.domain.task.Task
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.use_cases.GetAssignedMembersListUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentUserIdUseCase

class TaskListViewModel : ViewModel() {

    private val repositoryBoard = BoardRepositoryImpl
    private val repositoryUser = UserRepositoryImpl

    private val getBoardDetailsUseCase = GetBoardDetailsUseCase(repositoryBoard)
    private val getAssignedMembersListUseCase = GetAssignedMembersListUseCase(repositoryUser)
    private val getCurrentUserIdUseCase = GetCurrentUserIdUseCase(repositoryUser)
    private val addUpdateTaskListUseCase = AddUpdateTaskListUseCase(repositoryBoard)

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
}