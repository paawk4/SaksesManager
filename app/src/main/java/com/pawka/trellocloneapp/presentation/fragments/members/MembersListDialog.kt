package com.pawka.trellocloneapp.presentation.fragments.members

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.domain.user.User

abstract class MembersListDialog(
    context: Context,
    private var list: ArrayList<User>,
    private val title: String = ""
) : Dialog(context) {

    private var adapter: MemberItemAdapter? = null

    private lateinit var dialogTitleTv: TextView
    private lateinit var dialogListRv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null)
        dialogTitleTv = view.findViewById(R.id.dialog_title_tv)
        dialogListRv = view.findViewById(R.id.dialog_list_rv)
        setContentView(view)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        dialogTitleTv.text = title

        if (list.size > 0) {
            dialogListRv.layoutManager = LinearLayoutManager(context)
            adapter = MemberItemAdapter(context, list)
            dialogListRv.adapter = adapter
            adapter!!.setOnClickListener(object : MemberItemAdapter.OnClickListener {
                override fun onClick(position: Int, user: User, action: String) {
                    dismiss()
                    onItemSelected(user, action)
                }
            })
        }
    }

    protected abstract fun onItemSelected(user: User, color: String)
}