package com.pawka.trellocloneapp.presentation.fragments.boards

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.utils.Constants.APP_ACTIVITY
import com.pawka.trellocloneapp.utils.Constants.NAV_CONTROLLER

class BoardsFragment : Fragment() {

    private lateinit var boardsListRv: RecyclerView
    private lateinit var noBoardsTv: TextView
    private lateinit var createBoardBtn: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_boards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        configureToolbar()

        createBoardBtn.setOnClickListener {
            NAV_CONTROLLER.navigate(R.id.action_boardsFragment_to_createBoardFragment)
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

    fun populateBoardsToUI(boardsList: ArrayList<Board>) {
        if (boardsList.size > 0) {
            boardsListRv.visibility = View.VISIBLE
            noBoardsTv.visibility = View.GONE

            boardsListRv.layoutManager = LinearLayoutManager(APP_ACTIVITY)
            boardsListRv.setHasFixedSize(true)

            val adapter = BoardsListAdapter(boardsList)
            boardsListRv.adapter = adapter

            adapter.setOnClickListener(object : BoardsListAdapter.OnClickListener {
                override fun onClick(position: Int, model: Board) {
//                    val intent = Intent(this@MainActivity, TaskListActivity::class.java)
//                    intent.putExtra(Constants.DOCUMENT_ID, model.documentID)
//                    startActivity(intent)
                }
            })
        } else {
            boardsListRv.visibility = View.GONE
            noBoardsTv.visibility = View.VISIBLE
        }
    }
}