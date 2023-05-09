package com.pawka.trellocloneapp.presentation.fragments.task_list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.task.Task
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.presentation.fragments.BaseFragment
import com.pawka.trellocloneapp.utils.APP_ACTIVITY
import com.pawka.trellocloneapp.utils.NAV_CONTROLLER

class TaskListFragment : BaseFragment(R.layout.fragment_task_list) {

    private lateinit var viewModel: TaskListViewModel
    private lateinit var assignedMembersDetailList: ArrayList<User>
    private var currentBoard = Board()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TaskListViewModel::class.java]
        showProgressDialog()
        setHasOptionsMenu(true)

        val boardId = arguments?.getString("boardId")
        boardId?.let { observeViewModel(it) }
    }

    private fun configureToolbar(name: String) {
        APP_ACTIVITY.toolbar.title = name
        APP_ACTIVITY.appDrawer.disableDrawer()
        APP_ACTIVITY.toolbar.setNavigationIcon(R.drawable.ic_back)
    }

    private fun observeViewModel(boardId: String) {
        viewModel.getBoardsDetails(boardId) { board ->
            configureToolbar(board.name)
            hideProgressDialog()
            currentBoard = board
            viewModel.getAssignedMembersList(board.assignedTo)
        }
        viewModel.assignedMembersListLiveData.observe(viewLifecycleOwner) {
            boardMembersDetailList(it)
        }
    }

    private fun boardMembersDetailList(list: ArrayList<User>) {
        assignedMembersDetailList = list
        hideProgressDialog()

        val addTaskList = Task("Add List")
        mBoardDetails.taskList.add(addTaskList)

        task_list_rv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        task_list_rv.setHasFixedSize(true)

        val adapter = TaskItemAdapter(this, mBoardDetails.taskList)
        task_list_rv.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_list_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_members -> {
                val bundle = Bundle()
                bundle.putString("boardId", currentBoard.documentID)
                NAV_CONTROLLER.navigate(
                    R.id.action_taskListFragment_to_membersFragment,
                    bundle
                )
            }
        }
        return true
    }
}