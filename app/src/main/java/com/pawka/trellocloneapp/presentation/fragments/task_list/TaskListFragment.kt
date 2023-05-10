package com.pawka.trellocloneapp.presentation.fragments.task_list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.card.Card
import com.pawka.trellocloneapp.domain.task.Task
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.presentation.fragments.BaseFragment
import com.pawka.trellocloneapp.utils.APP_ACTIVITY
import com.pawka.trellocloneapp.utils.NAV_CONTROLLER

class TaskListFragment : BaseFragment(R.layout.fragment_task_list) {

    private lateinit var viewModel: TaskListViewModel
    private lateinit var assignedMembersDetailList: ArrayList<User>
    private lateinit var taskListRv: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskListRv = view.findViewById(R.id.task_list_rv)
        viewModel = ViewModelProvider(this)[TaskListViewModel::class.java]
        showProgressDialog()
        setHasOptionsMenu(true)

        val boardId = arguments?.getString("boardId")!!
        viewModel.getBoardsDetails(boardId) { board ->
            configureToolbar(board.name)
            viewModel.getAssignedMembersList(board.assignedTo) {
                observeViewModel()
            }
        }
    }

    private fun configureToolbar(name: String) {
        APP_ACTIVITY.toolbar.title = name
        APP_ACTIVITY.appDrawer.disableDrawer()
        APP_ACTIVITY.toolbar.setNavigationIcon(R.drawable.ic_back)
    }

    private fun observeViewModel() {
        viewModel.assignedMembersListLiveData.observe(viewLifecycleOwner) {
            assignedMembersDetailList = it

            viewModel.currentBoardLiveData.observe(viewLifecycleOwner) {board ->
                showBoardsDetails(board)
            }
        }
    }

    private fun showBoardsDetails(board: Board) {
        val addTaskList = Task("Add List")
        board.taskList.add(addTaskList)

        taskListRv.layoutManager =
            LinearLayoutManager(APP_ACTIVITY, LinearLayoutManager.HORIZONTAL, false)
        taskListRv.setHasFixedSize(true)

        val adapter = TaskListItemAdapter(board.taskList, viewModel, this)
        taskListRv.adapter = adapter
        hideProgressDialog()
    }

    fun createTaskList(taskName: String) {
        val currentUserId = viewModel.getCurrentUserId()
        currentUserId?.let { id ->
            val task = Task(taskName, id)
            viewModel.currentBoardLiveData.value?.let {
                it.taskList.add(0, task)
                it.taskList.removeAt(it.taskList.size - 1)

                showProgressDialog()
                viewModel.addUpdateTaskList(it) {
                    hideProgressDialog()
                }
            }
        }
    }

    fun updateTaskList(position: Int, listName: String, model: Task) {
        val task = Task(listName, model.createdBy)
        viewModel.currentBoardLiveData.value?.let {
            it.taskList[position] = task
            it.taskList.removeAt(it.taskList.size - 1)

            showProgressDialog()
            viewModel.addUpdateTaskList(it) {
                hideProgressDialog()
            }
        }
    }

    fun addCardToTask(position: Int, cardName: String) {
        viewModel.currentBoardLiveData.value?.let {
            it.taskList.removeAt(it.taskList.size - 1)

            val cardAssignedUsersList: ArrayList<String> = ArrayList()
            val currentUserID = viewModel.getCurrentUserId()
            currentUserID?.let { id ->
                cardAssignedUsersList.add(id)
                val card = Card(cardName, id, cardAssignedUsersList)

                val cardList = it.taskList[position].cards
                cardList.add(card)

                val task = Task(
                    it.taskList[position].title,
                    it.taskList[position].createdBy,
                    cardList
                )

                it.taskList[position] = task

                showProgressDialog()
                viewModel.addUpdateTaskList(it) {
                    hideProgressDialog()
                }
            }
        }
    }

    fun cardDetails(taskListPosition: Int, cardPosition: Int) {
        val bundle = Bundle()
        bundle.putString("boardId", viewModel.currentBoardLiveData.value?.documentID)
        bundle.putInt("taskListItemPosition", taskListPosition)
        bundle.putInt("cardListItemPosition", cardPosition)
        NAV_CONTROLLER.navigate(R.id.action_taskListFragment_to_cardDetailsFragment, bundle)
    }

    fun updateCardsInTaskList(taskListPosition: Int, cards: ArrayList<Card>) {
        viewModel.currentBoardLiveData.value?.let {
            it.taskList.removeAt(it.taskList.size - 1)
            it.taskList[taskListPosition].cards = cards
            showProgressDialog()
            viewModel.addUpdateTaskList(it) {
                hideProgressDialog()
            }
        }
    }

    fun deleteTaskList(position: Int) {
        viewModel.currentBoardLiveData.value?.let {
            it.taskList.removeAt(position)
            it.taskList.removeAt(it.taskList.size - 1)

            showProgressDialog()
            viewModel.addUpdateTaskList(it) {
                hideProgressDialog()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_list_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_members -> {
                val bundle = Bundle()
                bundle.putString("boardId", viewModel.currentBoardLiveData.value?.documentID)
                NAV_CONTROLLER.navigate(
                    R.id.action_taskListFragment_to_membersFragment,
                    bundle
                )
            }
        }
        return true
    }
}