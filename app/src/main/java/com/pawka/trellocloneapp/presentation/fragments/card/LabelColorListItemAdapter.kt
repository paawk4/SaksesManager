package com.pawka.trellocloneapp.presentation.fragments.card

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pawka.trellocloneapp.R

class LabelColorListItemAdapter(
    private val context: Context,
    private var list: ArrayList<String>,
    private val mSelectedColor: String
) : RecyclerView.Adapter<LabelColorListItemViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LabelColorListItemViewHolder {
        return LabelColorListItemViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_label_color, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LabelColorListItemViewHolder, position: Int) {
        val item = list[position]

        holder.viewMain.setBackgroundColor(Color.parseColor(item))
        if (item == mSelectedColor) {
            holder.selectedColorIv.visibility = View.VISIBLE
        } else {
            holder.selectedColorIv.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onClick(position, item)
            }
        }
    }


    interface OnItemClickListener {
        fun onClick(position: Int, color: String)
    }
}