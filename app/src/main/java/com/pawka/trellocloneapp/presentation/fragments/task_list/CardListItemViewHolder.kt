package com.pawka.trellocloneapp.presentation.fragments.task_list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pawka.trellocloneapp.R

class CardListItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val cardNameTv: TextView = view.findViewById(R.id.card_name_tv)

    val viewLabelColor: View = view.findViewById(R.id.view_label_color)

    val cardSelectedMembersListRv: RecyclerView = view.findViewById(R.id.card_selected_members_list_rv)
}