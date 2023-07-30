package com.pawka.trellocloneapp.presentation.fragments.members

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.use_cases.AssignMemberToBoardUseCase
import com.pawka.trellocloneapp.domain.board.use_cases.GetBoardDetailsUseCase
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.use_cases.GetAssignedMembersListUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.GetMemberDetailsUseCase

class MembersViewModel(
    private val getBoardDetailsUseCase: GetBoardDetailsUseCase,
    private val assignMemberToBoardUseCase: AssignMemberToBoardUseCase,
    private val getAssignedMembersListUseCase: GetAssignedMembersListUseCase,
    private val getMemberDetailsUseCase: GetMemberDetailsUseCase
) : ViewModel() {

    var assignedMembersListLiveData = MutableLiveData<ArrayList<User>>()

    fun getBoardsDetails(boardId: String, callback: (Board) -> Unit) {
        getBoardDetailsUseCase.getBoardsDetails(boardId, callback)
    }

    fun getAssignedMembersList(assignedTo: ArrayList<String>, callback: () -> Unit) {
        assignedMembersListLiveData =
            getAssignedMembersListUseCase.getAssignedMembersList(assignedTo, callback)
    }

    fun getMemberDetails(email: String, callback: (User) -> Unit) {
        getMemberDetailsUseCase.getMemberDetails(email, callback)
    }

    fun assignMemberToBoard(board: Board, callback: () -> Unit) {
        assignMemberToBoardUseCase.assignMemberToBoard(board, callback)
    }

}