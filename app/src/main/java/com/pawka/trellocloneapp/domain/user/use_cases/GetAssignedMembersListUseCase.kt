package com.pawka.trellocloneapp.domain.user.use_cases

import androidx.lifecycle.MutableLiveData
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.domain.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetAssignedMembersListUseCase: KoinComponent {
    private val userRepository: UserRepository by inject()
    fun getAssignedMembersList(assignedTo: ArrayList<String>, callback: () -> Unit): MutableLiveData<ArrayList<User>> {
        return userRepository.getAssignedMembersList(assignedTo, callback)
    }
}