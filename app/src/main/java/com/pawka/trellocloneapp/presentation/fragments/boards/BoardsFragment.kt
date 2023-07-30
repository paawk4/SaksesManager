package com.pawka.trellocloneapp.presentation.fragments.boards

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.presentation.fragments.BaseFragment
import com.pawka.trellocloneapp.utils.APP_ACTIVITY
import com.pawka.trellocloneapp.utils.NAV_CONTROLLER
import org.koin.androidx.viewmodel.ext.android.viewModel

class BoardsFragment : BaseFragment(R.layout.fragment_boards) {

    private lateinit var boardsListRv: RecyclerView
    private lateinit var noBoardsTv: TextView
    private lateinit var createBoardBtn: FloatingActionButton

    private val viewModel by viewModel<BoardsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressDialog()
        initViews(view)
        configureToolbar()
        observeViewModel()


        createBoardBtn.setOnClickListener {
            NAV_CONTROLLER.navigate(R.id.action_boardsFragment_to_createBoardFragment)
        }
    }

    private fun observeViewModel() {
        viewModel.boardsListLiveData.observe(viewLifecycleOwner) {
            hideProgressDialog()
            if (it.isNotEmpty()) {
                populateBoardsToUI(it)
            }
        }
    }

    private fun initViews(view: View) {
        boardsListRv = view.findViewById(R.id.boards_list_rv)
        noBoardsTv = view.findViewById(R.id.no_boards_tv)
        createBoardBtn = view.findViewById(R.id.boards_add_btn)
    }

    private fun configureToolbar() {
        APP_ACTIVITY.appDrawer.create()
        APP_ACTIVITY.appDrawer.enableDrawer()
        APP_ACTIVITY.toolbar.visibility = View.VISIBLE
        APP_ACTIVITY.toolbar.title = getString(R.string.app_name)
        APP_ACTIVITY.toolbar.setBackgroundResource(R.color.colorPrimaryDark)
        APP_ACTIVITY.toolbar.setTitleTextColor(Color.WHITE)
    }

    private fun populateBoardsToUI(boardsList: ArrayList<Board>) {
        if (boardsList.size > 0) {
            boardsListRv.visibility = View.VISIBLE
            noBoardsTv.visibility = View.GONE

            boardsListRv.layoutManager = LinearLayoutManager(APP_ACTIVITY)
            boardsListRv.setHasFixedSize(true)

            val adapter = BoardsListAdapter(boardsList)
            boardsListRv.adapter = adapter

            adapter.setOnClickListener(object : BoardsListAdapter.OnClickListener {
                override fun onClick(position: Int, model: Board) {
                    val bundle = Bundle()
                    bundle.putString("boardId", model.documentID)
                    NAV_CONTROLLER.navigate(R.id.action_boardsFragment_to_taskListFragment, bundle)
                }
            })
        } else {
            boardsListRv.visibility = View.GONE
            noBoardsTv.visibility = View.VISIBLE
        }
    }
}