package com.pawka.trellocloneapp.presentation.fragments.card

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.pawka.trellocloneapp.R

class LabelColorListItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val viewMain: View = view.findViewById(R.id.view_main)
    val selectedColorIv: ImageView = view.findViewById(R.id.selected_color_iv)
}