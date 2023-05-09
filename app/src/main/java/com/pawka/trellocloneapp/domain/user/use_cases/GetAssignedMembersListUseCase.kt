package com.pawka.trellocloneapp.domain.user.use_cases

import androidx.lifecycle.MutableLiveData
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.UserRepository

class GetAssignedMembersListUseCase(private val repository: UserRepository) {

    fun getAssignedMembersList(assignedTo: ArrayList<String>):MutableLiveData<ArrayList<User>> {
        return repository.getAssignedMembersList(assignedTo)
    }
}