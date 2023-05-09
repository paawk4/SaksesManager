package com.pawka.trellocloneapp.presentation.fragments.create_board

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.data.BoardRepositoryImpl
import com.pawka.trellocloneapp.data.UserRepositoryImpl
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.use_cases.CreateBoardUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentUserDataUseCase
import com.pawka.trellocloneapp.utils.NAV_CONTROLLER
import com.pawka.trellocloneapp.utils.parseString

class CreateBoardViewModel : ViewModel() {

    private val repositoryBoard = BoardRepositoryImpl
    private val repositoryUser = UserRepositoryImpl

    private val createBoardUseCase = CreateBoardUseCase(repositoryBoard)
    private val getCurrentUserUseCase = GetCurrentUserDataUseCase(repositoryUser)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    fun createBoard(nameBoard: String, imageBoard: String = "", callback: () -> Unit) {
        if (parseString(nameBoard).isNotBlank()) {
            val assignedUsers = ArrayList<String>()
            val currentUser = getCurrentUserUseCase.getCurrentUserData()
            currentUser.value?.let { user ->
                assignedUsers.add(user.id)
                val board = Board(
                    nameBoard,
                    imageBoard,
                    user.name,
                    assignedUsers
                )
                createBoardUseCase.createBoard(board, callback)
            }
        } else {
            _errorInputName.value = true
        }
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }
}