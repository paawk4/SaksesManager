package com.pawka.trellocloneapp.presentation.fragments.create_board

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.use_cases.CreateBoardUseCase
import com.pawka.trellocloneapp.domain.board.use_cases.SetBoardImageUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentUserDataUseCase
import com.pawka.trellocloneapp.utils.parseString

class CreateBoardViewModel(
    private val createBoardUseCase: CreateBoardUseCase,
    private val getCurrentUserUseCase: GetCurrentUserDataUseCase,
    private val setBoardImageUseCase: SetBoardImageUseCase
) : ViewModel() {

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

    fun putImageToStorage(uri: Uri, callback: () -> Unit) {
        setBoardImageUseCase.setBoardImage(uri, callback)
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }
}