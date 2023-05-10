package com.pawka.trellocloneapp.presentation.fragments.task_list

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.domain.card.Card
import com.pawka.trellocloneapp.domain.selected_members.SelectedMembers
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.utils.APP_ACTIVITY

open class CardListItemAdapter(
    private var list: ArrayList<Card>,
    viewModel: TaskListViewModel,
) :
    RecyclerView.Adapter<CardListItemViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private var assignedMembersList = viewModel.assignedMembersListLiveData.value!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListItemViewHolder {
        return CardListItemViewHolder(
            LayoutInflater.from(APP_ACTIVITY)
                .inflate(R.layout.item_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(
        holder: CardListItemViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val model = list[position]

        holder.cardNameTv.text = model.name

        if (model.labelColor.isNotEmpty()) {
            holder.viewLabelColor.visibility = View.VISIBLE
            holder.viewLabelColor.setBackgroundColor(
                Color.parseColor(model.labelColor)
            )
        } else {
            holder.viewLabelColor.visibility = View.GONE
        }
        if (assignedMembersList.size > 0) {
            val selectedMembersList: ArrayList<SelectedMembers> = ArrayList()

            for (i in assignedMembersList.indices) {
                for (j in model.assignedTo) {
                    if (assignedMembersList[i].id == j) {
                        val selectedMember =
                            SelectedMembers(
                                assignedMembersList[i].id,
                                assignedMembersList[i].image
                            )
                        selectedMembersList.add(selectedMember)
                    }
                }
            }

            if (selectedMembersList.size > 0) {
                if (selectedMembersList.size == 1
                    && selectedMembersList[0].id == model.createdBy
                ) {
                    holder.cardSelectedMembersListRv.visibility = View.GONE
                } else {
                    holder.cardSelectedMembersListRv.visibility = View.VISIBLE
                    holder.cardSelectedMembersListRv.layoutManager =
                        GridLayoutManager(APP_ACTIVITY, 4)
                    val adapter = CardMemberListItemAdapter(selectedMembersList, false)
                    holder.cardSelectedMembersListRv.adapter = adapter
                    adapter.setOnClickListener(object :
                        CardMemberListItemAdapter.OnClickListener {
                        override fun onClick() {
                            if (onClickListener != null) {
                                onClickListener!!.onClick(position)
                            }
                        }
                    })
                }
            } else {
                holder.cardSelectedMembersListRv.visibility = View.GONE
            }
        }

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position)
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener

    }

    interface OnClickListener {
        fun onClick(position: Int)
    }
}