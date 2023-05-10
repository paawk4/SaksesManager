package com.pawka.trellocloneapp.presentation.fragments.task_list

import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pawka.trellocloneapp.R

class TaskListItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val addTaskListTv: TextView = view.findViewById(R.id.add_task_list_tv)
    val addCardTv: TextView = view.findViewById(R.id.add_card_tv)
    val taskListTitleTv: TextView = view.findViewById(R.id.task_list_title_tv)

    val taskListNameEt: EditText = view.findViewById(R.id.task_list_name_et)
    val cardNameEt: EditText = view.findViewById(R.id.card_name_et)
    val editTaskListNameEt: EditText = view.findViewById(R.id.edit_task_list_name_et)

    val taskItemLl: LinearLayout = view.findViewById(R.id.task_item_ll)
    val titleViewLl: LinearLayout = view.findViewById(R.id.title_view_ll)

    val addTaskListNameCv: CardView = view.findViewById(R.id.add_task_list_name_cv)
    val addCardCv: CardView = view.findViewById(R.id.add_card_cv)
    val editTaskListNameCv: CardView = view.findViewById(R.id.edit_task_list_name_cv)

    val closeListNameIb: ImageButton = view.findViewById(R.id.close_list_name_ib)
    val closeCardNameIb: ImageButton = view.findViewById(R.id.close_card_name_ib)
    val closeEditableViewIb: ImageButton = view.findViewById(R.id.close_editable_view_ib)
    val doneListNameIb: ImageButton = view.findViewById(R.id.done_list_name_ib)
    val doneEditListNameIb: ImageButton = view.findViewById(R.id.done_edit_list_name_ib)
    val doneCardNameIb: ImageButton = view.findViewById(R.id.done_card_name_ib)
    val editListNameIb: ImageButton = view.findViewById(R.id.edit_list_name_ib)
    val deleteListIb: ImageButton = view.findViewById(R.id.delete_list_ib)

    val cardListRv: RecyclerView = view.findViewById(R.id.card_list_rv)
}