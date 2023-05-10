package com.pawka.trellocloneapp.presentation.fragments.task_list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pawka.trellocloneapp.R

class CardMemberListItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val addMemberIv: ImageView = view.findViewById(R.id.add_member_iv)
    val selectedMemberImageIv: ImageView = view.findViewById(R.id.selected_member_image_iv)
}