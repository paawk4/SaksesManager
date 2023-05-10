package com.pawka.trellocloneapp.presentation.fragments.task_list

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.domain.task.Task
import com.pawka.trellocloneapp.utils.APP_ACTIVITY
import com.pawka.trellocloneapp.utils.showToast
import java.util.*
import kotlin.collections.ArrayList

open class TaskListItemAdapter(
    private var list: ArrayList<Task>,
    private var viewModel: TaskListViewModel,
    private var context: TaskListFragment
) :
    RecyclerView.Adapter<TaskListItemViewHolder>() {

    private var mPositionDraggedFrom = -1
    private var mPositionDraggedTo = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListItemViewHolder {
        val view = LayoutInflater.from(APP_ACTIVITY).inflate(R.layout.item_task, parent, false)

        val layoutParams = LinearLayout.LayoutParams(
            (parent.width * 0.7).toInt(),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins((15.toDP()).toPX(), 0, (40.toDP()).toPX(), 0)
        view.layoutParams = layoutParams
        return TaskListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(
        holder: TaskListItemViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val model = list[position]

        if (position == list.size - 1) {
            holder.addTaskListTv.visibility = View.VISIBLE
            holder.taskItemLl.visibility = View.GONE
        } else {
            holder.addTaskListTv.visibility = View.GONE
            holder.taskItemLl.visibility = View.VISIBLE
        }

        holder.taskListTitleTv.text = model.title
        holder.addTaskListTv.setOnClickListener {
            holder.addTaskListTv.visibility = View.GONE
            holder.addTaskListNameCv.visibility = View.VISIBLE
        }

        holder.closeListNameIb.setOnClickListener {
            holder.addTaskListTv.visibility = View.VISIBLE
            holder.addTaskListNameCv.visibility = View.GONE
        }

        holder.doneListNameIb.setOnClickListener {
            val listName = holder.taskListNameEt.text.toString()

            if (listName.isNotEmpty()) {
                context.createTaskList(listName)
            } else {
                showToast("Введите название списка")
            }
        }

        holder.editListNameIb.setOnClickListener {
            holder.editTaskListNameEt.setText(model.title)
            holder.titleViewLl.visibility = View.GONE
            holder.editTaskListNameCv.visibility = View.VISIBLE
        }

        holder.closeEditableViewIb.setOnClickListener {
            holder.titleViewLl.visibility = View.VISIBLE
            holder.editTaskListNameCv.visibility = View.GONE
        }

        holder.doneEditListNameIb.setOnClickListener {
            val listName = holder.editTaskListNameEt.text.toString()
            if (listName.isNotEmpty()) {
                context.updateTaskList(position, listName, model)
            } else {
                showToast("Please enter a list name")
            }
        }

        holder.deleteListIb.setOnClickListener {
            alertDialogForDeleteList(position, model.title)
        }

        holder.addCardTv.setOnClickListener {
            holder.addCardTv.visibility = View.GONE
            holder.addCardCv.visibility = View.VISIBLE
        }

        holder.closeCardNameIb.setOnClickListener {
            holder.addCardTv.visibility = View.VISIBLE
            holder.addCardCv.visibility = View.GONE
        }

        holder.doneCardNameIb.setOnClickListener {
            val cardName = holder.cardNameEt.text.toString()
            if (cardName.isNotEmpty()) {
                context.addCardToTask(position, cardName)
            } else {
                showToast("Please enter a card name")
            }
        }

        holder.cardListRv.layoutManager = LinearLayoutManager(APP_ACTIVITY)
        holder.cardListRv.setHasFixedSize(true)

        val adapter = CardListItemAdapter(model.cards, viewModel)
        holder.cardListRv.adapter = adapter

        adapter.setOnClickListener(object :
            CardListItemAdapter.OnClickListener {
            override fun onClick(position: Int) {
                context.cardDetails(position, position)
            }
        })

        val dividerItemDecoration = DividerItemDecoration(
            APP_ACTIVITY,
            DividerItemDecoration.VERTICAL
        )

        holder.cardListRv.addItemDecoration(dividerItemDecoration)

        val helper = ItemTouchHelper(
            object :
                ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val draggedPosition = viewHolder.adapterPosition
                    val targetPosition = target.adapterPosition

                    if (mPositionDraggedFrom == -1) {
                        mPositionDraggedFrom = draggedPosition
                    }

                    mPositionDraggedTo = targetPosition
                    Collections.swap(list[position].cards, draggedPosition, targetPosition)
                    adapter.notifyItemMoved(draggedPosition, targetPosition)
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                }

                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ) {
                    super.clearView(recyclerView, viewHolder)
                    //if any change was done update the cards list task
                    if (mPositionDraggedFrom != -1 && mPositionDraggedTo != -1 && mPositionDraggedFrom != mPositionDraggedTo) {
                        context.updateCardsInTaskList(
                            position,
                            list[position].cards
                        )
                    }
                    // reset values
                    mPositionDraggedFrom = -1
                    mPositionDraggedTo = -1
                }
            }
        )

        helper.attachToRecyclerView(holder.cardListRv)

    }

    private fun alertDialogForDeleteList(position: Int, title: String) {
        val builder = AlertDialog.Builder(APP_ACTIVITY)
        builder.setTitle("Alert!")
        builder.setMessage("Are you sure you want to delete $title?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
            context.deleteTaskList(position)
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun Int.toDP(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

    private fun Int.toPX(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}