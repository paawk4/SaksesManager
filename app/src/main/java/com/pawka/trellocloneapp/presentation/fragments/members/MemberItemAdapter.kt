package com.pawka.trellocloneapp.presentation.fragments.members

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.domain.user.User

open class MemberItemAdapter(private val context: Context, private var list: ArrayList<User>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_member, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            val memberNameTv = holder.itemView.findViewById<TextView>(R.id.member_name_tv)
            val memberEmailTv = holder.itemView.findViewById<TextView>(R.id.member_email_tv)
            val memberImageIv = holder.itemView.findViewById<ImageView>(R.id.member_image_iv)
            val selectedMemberIv = holder.itemView.findViewById<ImageView>(R.id.selected_member_iv)
            memberNameTv.text = model.name
            memberEmailTv.text = model.email
            Glide
                .with(context)
                .load(model.image)
                .fitCenter()
                .placeholder(R.drawable.ic_user_place_holder)
                .into(memberImageIv)

            if (model.selected) {
                selectedMemberIv.visibility = View.VISIBLE
            } else {
                selectedMemberIv.visibility = View.GONE
            }

            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    if (model.selected) {
                        onClickListener!!.onClick(position, model, UN_SELECT)
                    } else {
                        onClickListener!!.onClick(position, model, SELECT)
                    }
                }
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, user: User, action: String)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        private const val SELECT: String = "select"
        private const val UN_SELECT = "unselect"
    }
}