package com.pawka.trellocloneapp.presentation.fragments.members

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.presentation.fragments.BaseFragment
import com.pawka.trellocloneapp.utils.APP_ACTIVITY
import com.pawka.trellocloneapp.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MembersFragment : BaseFragment(R.layout.fragment_members) {

    private val viewModel by viewModel<MembersViewModel>()
    private lateinit var membersListRv: RecyclerView
    private lateinit var assignedMembersList: ArrayList<User>
    private var boardDetails = Board()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initViews(view)

        showProgressDialog()
        val boardId = arguments?.getString("boardId")
        boardId?.let {
            viewModel.getBoardsDetails(boardId) {
                boardDetails = it
                viewModel.getAssignedMembersList(boardDetails.assignedTo) {
                    observeViewModel()
                }
                configureToolbar()
            }
        }
        hideProgressDialog()
    }

    private fun configureToolbar() {
        APP_ACTIVITY.toolbar.title = boardDetails.name + " участники"
    }

    private fun initViews(view: View) {
        membersListRv = view.findViewById(R.id.members_list_rv)
    }

    private fun observeViewModel() {
        viewModel.assignedMembersListLiveData.observe(viewLifecycleOwner) {
            assignedMembersList = it
            membersListRv.layoutManager =
                LinearLayoutManager(APP_ACTIVITY, LinearLayoutManager.VERTICAL, false)
            membersListRv.setHasFixedSize(true)

            val adapter = MemberItemAdapter(APP_ACTIVITY, it)
            membersListRv.adapter = adapter
        }

    }

    private fun dialogSearchMember() {
        val dialog = Dialog(APP_ACTIVITY)
        dialog.setContentView(R.layout.dialog_serach_member)

        dialog.findViewById<TextView>(R.id.add_tv).setOnClickListener {
            val email = dialog.findViewById<EditText>(R.id.email_search_member_et).text.toString()
            if (email.isNotEmpty()) {
                showProgressDialog()
                viewModel.getMemberDetails(email) {
                    if (it != User()) {
                        memberDetails(it)
                    } else {
                        hideProgressDialog()
                        showToast("Такой участник не найден")
                    }

                }
                dialog.dismiss()
            } else {
                showToast("Введите почту")
            }
        }

        dialog.findViewById<TextView>(R.id.cancel_tv).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun memberDetails(user: User) {
        boardDetails.assignedTo.add(user.id)
        viewModel.assignMemberToBoard(boardDetails) {
            memberAssignSuccess(user)
        }
    }

    private fun memberAssignSuccess(user: User) {
        hideProgressDialog()
        assignedMembersList.add(user)
        viewModel.assignedMembersListLiveData.value = assignedMembersList
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_member, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_member -> {
                dialogSearchMember()
            }
        }
        return true
    }
}