package com.pawka.trellocloneapp.di

import com.pawka.trellocloneapp.data.BoardRepositoryImpl
import com.pawka.trellocloneapp.data.UserRepositoryImpl
import com.pawka.trellocloneapp.domain.board.BoardRepository
import com.pawka.trellocloneapp.domain.board.use_cases.AddUpdateTaskListUseCase
import com.pawka.trellocloneapp.domain.board.use_cases.AssignMemberToBoardUseCase
import com.pawka.trellocloneapp.domain.board.use_cases.CreateBoardUseCase
import com.pawka.trellocloneapp.domain.board.use_cases.DeleteBoardUseCase
import com.pawka.trellocloneapp.domain.board.use_cases.GetBoardDetailsUseCase
import com.pawka.trellocloneapp.domain.board.use_cases.GetBoardsListUseCase
import com.pawka.trellocloneapp.domain.board.use_cases.SetBoardImageUseCase
import com.pawka.trellocloneapp.domain.user.UserRepository
import com.pawka.trellocloneapp.domain.user.use_cases.GetAssignedMembersListUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentFirebaseUserUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentUserDataUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.GetCurrentUserIdUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.GetMemberDetailsUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.SetProfileImageUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.SignInUserUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.SignOutUserUseCase
import com.pawka.trellocloneapp.domain.user.use_cases.SignUpUserUseCase
import com.pawka.trellocloneapp.presentation.app_drawer.AppDrawer
import com.pawka.trellocloneapp.presentation.app_drawer.AppDrawerViewModel
import com.pawka.trellocloneapp.presentation.fragments.boards.BoardsViewModel
import com.pawka.trellocloneapp.presentation.fragments.card.CardDetailsViewModel
import com.pawka.trellocloneapp.presentation.fragments.create_board.CreateBoardViewModel
import com.pawka.trellocloneapp.presentation.fragments.members.MembersViewModel
import com.pawka.trellocloneapp.presentation.fragments.settings.SettingsViewModel
import com.pawka.trellocloneapp.presentation.fragments.sign_in.SignInViewModel
import com.pawka.trellocloneapp.presentation.fragments.sign_up.SignUpViewModel
import com.pawka.trellocloneapp.presentation.fragments.start.StartViewModel
import com.pawka.trellocloneapp.presentation.fragments.task_list.TaskListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoriesModule = module {
    single { AppDrawer(get()) }
    single<BoardRepository> { BoardRepositoryImpl }
    single<UserRepository> { UserRepositoryImpl }
}

val viewModelsModule = module {
    viewModel { StartViewModel(get()) }
    viewModel { BoardsViewModel(get()) }
    viewModel { TaskListViewModel(get(), get(), get(), get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { SettingsViewModel(get(), get(), get()) }
    viewModel { MembersViewModel(get(), get(), get(), get()) }
    viewModel { CreateBoardViewModel(get(), get(), get()) }
    viewModel { CardDetailsViewModel(get(), get(), get()) }
    viewModel { BoardsViewModel(get()) }
    viewModel { AppDrawerViewModel(get(), get()) }
}

val useCasesModule = module {
    single { GetBoardsListUseCase() }
    single { GetBoardDetailsUseCase() }
    single { CreateBoardUseCase() }
    single { SetBoardImageUseCase() }
    single { GetMemberDetailsUseCase() }
    single { DeleteBoardUseCase() }

    single { GetAssignedMembersListUseCase() }
    single { AddUpdateTaskListUseCase() }
    single { AssignMemberToBoardUseCase() }

    single { GetCurrentUserDataUseCase() }
    single { GetCurrentUserIdUseCase() }
    single { SignOutUserUseCase() }
    single { SetProfileImageUseCase() }
    single { SignInUserUseCase() }
    single { SignUpUserUseCase() }
    single { GetCurrentFirebaseUserUseCase() }

}