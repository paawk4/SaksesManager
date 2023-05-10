package com.pawka.trellocloneapp.presentation.fragments.task_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.domain.selected_members.SelectedMembers
import com.pawka.trellocloneapp.utils.APP_ACTIVITY

open class CardMemberListItemAdapter(
    private var list: ArrayList<SelectedMembers>,
    private val assignedMembers: Boolean
) : RecyclerView.Adapter<CardMemberListItemViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardMemberListItemViewHolder {
        return CardMemberListItemViewHolder(
            LayoutInflater.from(APP_ACTIVITY)
                .inflate(
                    R.layout.item_card_selected_member,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CardMemberListItemViewHolder, position: Int) {
        val model = list[position]

        if (position == list.size - 1 && assignedMembers) {
            holder.addMemberIv.visibility = View.VISIBLE
            holder.selectedMemberImageIv.visibility = View.GONE
        } else {
            holder.addMemberIv.visibility = View.GONE
            holder.selectedMemberImageIv.visibility = View.VISIBLE

            Glide
                .with(APP_ACTIVITY)
                .load(model.image)
                .fitCenter()
                .placeholder(R.drawable.ic_user_place_holder)
                .into(holder.selectedMemberImageIv)
        }

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick()
            }
        }

    }


    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick()
    }
}