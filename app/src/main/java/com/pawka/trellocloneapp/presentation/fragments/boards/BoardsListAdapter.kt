package com.pawka.trellocloneapp.presentation.fragments.boards

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.utils.APP_ACTIVITY

open class BoardsListAdapter(private var list: ArrayList<Board>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(APP_ACTIVITY)
                .inflate(R.layout.item_board, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            Glide
                .with(APP_ACTIVITY)
                .load(model.image)
                .centerCrop()
                .placeholder(R.drawable.ic_board_place_holder)
                .into(holder.itemView.findViewById(R.id.item_board_iv))
            val boardName = holder.itemView.findViewById<TextView>(R.id.item_board_name_tv)
            val boardCreatedBy =
                holder.itemView.findViewById<TextView>(R.id.item_board_created_by_tv)
            boardName.text = model.name
            boardCreatedBy.text = "Создан: " + model.createdBy
            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, model)
                }
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener

    }

    interface OnClickListener {
        fun onClick(position: Int, model: Board)
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}