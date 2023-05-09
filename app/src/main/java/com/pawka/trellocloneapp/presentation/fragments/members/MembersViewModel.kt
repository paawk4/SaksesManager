package com.pawka.trellocloneapp.presentation.fragments.members

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.data.BoardRepositoryImpl
import com.pawka.trellocloneapp.data.UserRepositoryImpl
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.use_cases.AssignMemberToBoardUseCase
import com.pawka.trellocloneapp.domain.board.use_cases.GetBoardDetailsUseCase
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.use_cases.GetAssignedMembersListUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentFirebaseUserUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.GetMemberDetailsUseCase

class MembersViewModel: ViewModel() {

    private val repositoryBoard = BoardRepositoryImpl
    private val repositoryUser = UserRepositoryImpl

    private val getBoardDetailsUseCase = GetBoardDetailsUseCase(repositoryBoard)
    private val assignMemberToBoardUseCase = AssignMemberToBoardUseCase(repositoryBoard)
    private val getAssignedMembersListUseCase = GetAssignedMembersListUseCase(repositoryUser)
    private val getMemberDetailsUseCase = GetMemberDetailsUseCase(repositoryUser)

    var assignedMembersListLiveData = MutableLiveData<ArrayList<User>>()

    fun getBoardsDetails(boardId: String, callback: (Board) -> Unit) {
        getBoardDetailsUseCase.getBoardsDetails(boardId, callback)
    }

    fun getAssignedMembersList(assignedTo: ArrayList<String>) {
        assignedMembersListLiveData =
            getAssignedMembersListUseCase.getAssignedMembersList(assignedTo)
    }

    fun getMemberDetails(email: String, callback: (User) -> Unit) {
        getMemberDetailsUseCase.getMemberDetails(email, callback)
    }

    fun assignMemberToBoard(board: Board, callback: () -> Unit) {
        assignMemberToBoardUseCase.assignMemberToBoard(board, callback)
    }

}