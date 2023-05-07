package com.pawka.trellocloneapp.presentation.fragments.create_board

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pawka.trellocloneapp.data.BoardRepositoryImpl
import com.pawka.trellocloneapp.data.UserRepositoryImpl
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.board.use_cases.CreateBoardUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentUserDataUseCase
import com.pawka.trellocloneapp.utils.Constants.parseString

class CreateBoardViewModel: ViewModel() {

    private val repository = BoardRepositoryImpl

    private val createBoardUseCase = CreateBoardUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    fun createBoard(nameBoard: String, imageBoard: String = "") {
        if (parseString(nameBoard).isNotBlank()) {
            createBoardUseCase.createBoard(Board(name = nameBoard, image = imageBoard))
        } else {
            _errorInputName.value = true
        }
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }
}