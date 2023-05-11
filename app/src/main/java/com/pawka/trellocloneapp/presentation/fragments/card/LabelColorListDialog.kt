package com.pawka.trellocloneapp.presentation.fragments.card

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pawka.trellocloneapp.R

abstract class LabelColorListDialog(
    context: Context,
    private var list: ArrayList<String>,
    private val title: String = "",
    private val mSelectedColor: String = ""
) : Dialog(context) {

    private lateinit var dialogTitleTv: TextView
    private lateinit var dialogListRv: RecyclerView

    private var adapter: LabelColorListItemAdapter? = null

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
        dialogListRv.layoutManager = LinearLayoutManager(context)
        adapter = LabelColorListItemAdapter(context, list, mSelectedColor)
        dialogListRv.adapter = adapter
        adapter!!.onItemClickListener = object : LabelColorListItemAdapter.OnItemClickListener {
            override fun onClick(position: Int, color: String) {
                dismiss()
                onItemSelected(color)
            }
        }
    }

    protected abstract fun onItemSelected(color: String)
}