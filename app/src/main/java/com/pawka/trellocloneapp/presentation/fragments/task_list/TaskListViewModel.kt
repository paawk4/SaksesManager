package com.pawka.trellocloneapp.presentation.fragments.task_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.data.BoardRepositoryImpl
import com.pawka.trellocloneapp.data.UserRepositoryImpl
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.use_cases.GetBoardDetailsUseCase
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.use_cases.GetAssignedMembersListUseCase

class TaskListViewModel : ViewModel() {

    private val repositoryBoard = BoardRepositoryImpl
    private val repositoryUser = UserRepositoryImpl

    private val getBoardDetailsUseCase = GetBoardDetailsUseCase(repositoryBoard)
    private val getAssignedMembersListUseCase = GetAssignedMembersListUseCase(repositoryUser)

    var assignedMembersListLiveData = MutableLiveData<ArrayList<User>>()

    fun getBoardsDetails(boardId: String, callback: (Board) -> Unit) {
        getBoardDetailsUseCase.getBoardsDetails(boardId, callback)
    }

    fun getAssignedMembersList(assignedTo: ArrayList<String>) {
        assignedMembersListLiveData =
            getAssignedMembersListUseCase.getAssignedMembersList(assignedTo)
    }

}