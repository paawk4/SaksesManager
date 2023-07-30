package com.pawka.trellocloneapp.presentation.fragments.card

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.domain.board.Board
import com.pawka.trellocloneapp.domain.card.Card
import com.pawka.trellocloneapp.domain.selected_members.SelectedMembers
import com.pawka.trellocloneapp.domain.user.User
import com.pawka.trellocloneapp.presentation.fragments.BaseFragment
import com.pawka.trellocloneapp.presentation.fragments.members.MembersListDialog
import com.pawka.trellocloneapp.presentation.fragments.task_list.CardMemberListItemAdapter
import com.pawka.trellocloneapp.utils.APP_ACTIVITY
import com.pawka.trellocloneapp.utils.NAV_CONTROLLER
import com.pawka.trellocloneapp.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Date
import java.util.Locale

class CardDetailsFragment : BaseFragment(R.layout.fragment_card_details) {

    private val viewModel by viewModel<CardDetailsViewModel>()

    private lateinit var nameCardDetailsEt: EditText
    private lateinit var selectLabelColorTv: TextView
    private lateinit var selectMembersTv: TextView
    private lateinit var selectDueDateTv: TextView
    private lateinit var updateCardDetailsBtn: Button
    private lateinit var selectedMembersListRv: RecyclerView

    private var memberDetailList = arrayListOf<User>()
    private var currentBoard = Board()
    private var taskListPosition = -1
    private var cardListPosition = -1
    private var selectedColor = ""
    private var selectedDueDateMS: Long = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initViews(view)
        showProgressDialog()
        getReceivedArguments {
            configureToolbar()
            outputDataOutputToTheScreen()
            hideProgressDialog()
        }
    }

    private fun initViews(view: View) {
        nameCardDetailsEt = view.findViewById(R.id.name_card_details_et)
        selectLabelColorTv = view.findViewById(R.id.select_label_color_tv)
        selectMembersTv = view.findViewById(R.id.select_members_tv)
        selectDueDateTv = view.findViewById(R.id.select_due_date_tv)
        updateCardDetailsBtn = view.findViewById(R.id.update_card_details_btn)
        selectedMembersListRv = view.findViewById(R.id.selected_members_list_rv)
    }

    private fun configureToolbar() {
        APP_ACTIVITY.toolbar.title =
            currentBoard.taskList[taskListPosition].cards[cardListPosition].name
    }

    private fun updateCardDetails() {
        val card = Card(
            nameCardDetailsEt.text.toString(),
            currentBoard.taskList[taskListPosition].cards[cardListPosition].createdBy,
            currentBoard.taskList[taskListPosition].cards[cardListPosition].assignedTo,
            selectedColor,
            selectedDueDateMS
        )

        currentBoard.taskList[taskListPosition].cards[cardListPosition] = card

        showProgressDialog()
        viewModel.addUpdateTaskList(currentBoard) {
            hideProgressDialog()
            NAV_CONTROLLER.popBackStack()
        }
    }

    private fun outputDataOutputToTheScreen() {
        nameCardDetailsEt.setText(currentBoard.taskList[taskListPosition].cards[cardListPosition].name)
        nameCardDetailsEt.setSelection(nameCardDetailsEt.text.toString().length)

        selectedColor = currentBoard.taskList[taskListPosition].cards[cardListPosition].labelColor
        if (selectedColor.isNotEmpty()) {
            setColor()
        }

        updateCardDetailsBtn.setOnClickListener {
            if (nameCardDetailsEt.text.toString().isNotEmpty()) {
                updateCardDetails()
            } else {
                showToast("Введите название карты")
            }
        }

        selectLabelColorTv.setOnClickListener {
            labelColorsListDialog()
        }

        selectMembersTv.setOnClickListener {
            membersListDialog()
        }

        selectedDueDateMS = currentBoard.taskList[taskListPosition].cards[cardListPosition].dueDate

        if (selectedDueDateMS > 0) {
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val selectedDate = simpleDateFormat.format(Date(selectedDueDateMS))
            selectDueDateTv.text = selectedDate
        }

        selectDueDateTv.setOnClickListener {
            showDatePicker()
        }

        setupSelectedMembersList()
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialogListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val sDayOfMonth = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                val sMonthOfYear =
                    if ((monthOfYear + 1) < 10) "0${monthOfYear + 1}" else "${monthOfYear + 1}"

                val selectedDate = "$sDayOfMonth/$sMonthOfYear/$year"
                selectDueDateTv.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
                val theDate = sdf.parse(selectedDate)
                selectedDueDateMS = theDate!!.time
            }

        val datePickerDialog = DatePickerDialog(
            APP_ACTIVITY,
            datePickerDialogListener,
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun membersListDialog() {
        val cardAssignedMembersList =
            currentBoard.taskList[taskListPosition].cards[cardListPosition].assignedTo

        if (cardAssignedMembersList.size > 0) {
            for (i in memberDetailList.indices) {
                for (j in cardAssignedMembersList) {
                    if (memberDetailList[i].id == j) {
                        memberDetailList[i].selected = true
                    }
                }
            }
        } else {
            for (i in memberDetailList.indices) {
                memberDetailList[i].selected = false
            }
        }
        val listDialog = object : MembersListDialog(
            APP_ACTIVITY,
            memberDetailList,
            "Выбрать участников"
        ) {
            override fun onItemSelected(user: User, action: String) {
                if (action == SELECT) {
                    if (!currentBoard
                            .taskList[taskListPosition]
                            .cards[cardListPosition]
                            .assignedTo.contains(user.id)
                    ) {
                        currentBoard
                            .taskList[taskListPosition]
                            .cards[cardListPosition]
                            .assignedTo.add(user.id)
                    }
                } else {
                    currentBoard
                        .taskList[taskListPosition]
                        .cards[cardListPosition]
                        .assignedTo.remove(user.id)

                    for (i in memberDetailList.indices) {
                        if (memberDetailList[i].id == user.id) {
                            memberDetailList[i].selected = false
                        }
                    }
                }
                setupSelectedMembersList()
            }
        }
        listDialog.show()
    }

    private fun setupSelectedMembersList() {
        val cardAssignedMembersList =
            currentBoard.taskList[taskListPosition].cards[cardListPosition].assignedTo
        val selectedMembersList: ArrayList<SelectedMembers> = ArrayList()

        for (i in memberDetailList.indices) {
            for (j in cardAssignedMembersList) {
                if (memberDetailList[i].id == j) {
                    val selectedMember =
                        SelectedMembers(memberDetailList[i].id, memberDetailList[i].image)
                    selectedMembersList.add(selectedMember)
                }
            }
        }
        if (selectedMembersList.size > 0) {
            selectedMembersList.add(SelectedMembers("", ""))
            selectMembersTv.visibility = View.GONE
            selectedMembersListRv.visibility = View.VISIBLE

            selectedMembersListRv.layoutManager = GridLayoutManager(
                APP_ACTIVITY, 6
            )

            val adapter = CardMemberListItemAdapter(selectedMembersList, true)
            selectedMembersListRv.adapter = adapter
            adapter.setOnClickListener(
                object : CardMemberListItemAdapter.OnClickListener {
                    override fun onClick() {
                        membersListDialog()
                    }
                }
            )
        } else {
            selectMembersTv.visibility = View.VISIBLE
            selectedMembersListRv.visibility = View.GONE
        }
    }

    private fun labelColorsListDialog() {
        val colorsList: ArrayList<String> = colorsList()
        val listDialog = object : LabelColorListDialog(
            APP_ACTIVITY,
            colorsList,
            "Выберите цвет текста",
            selectedColor
        ) {
            override fun onItemSelected(color: String) {
                selectedColor = color
                setColor()
            }
        }
        listDialog.show()
    }

    private fun setColor() {
        selectLabelColorTv.text = ""
        selectLabelColorTv.setBackgroundColor(Color.parseColor(selectedColor))
    }

    private fun colorsList(): ArrayList<String> {
        val colorsList: ArrayList<String> = ArrayList()
        colorsList.add("#0C90F1")
        colorsList.add("#F72400")
        colorsList.add("#90F700")
        colorsList.add("#8400F7")
        colorsList.add("#F700CE")
        colorsList.add("#FF9800")
        colorsList.add("#7A8089")

        return colorsList
    }


    private fun getReceivedArguments(callback: () -> Unit) {
        val boardId = arguments?.getString("boardId")
        boardId?.let { id ->
            viewModel.getBoardsDetails(id) {
                currentBoard = it
                viewModel.getAssignedMembersList(currentBoard.assignedTo) {
                    memberDetailList = viewModel.assignedMembersListLiveData.value ?: arrayListOf()
                }
                taskListPosition = arguments?.getInt("taskListItemPosition")!!
                cardListPosition = arguments?.getInt("cardListItemPosition")!!
                callback()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete_card, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_card -> {
                val cardTitle =
                    currentBoard.taskList[taskListPosition].cards[cardListPosition].name
                alertDialogForDeleteCard(cardTitle)
            }
        }
        return true
    }

    private fun alertDialogForDeleteCard(cardName: String) {
        val builder = AlertDialog.Builder(APP_ACTIVITY)

        builder.setTitle("Внимание!")
        builder.setMessage("Вы уверены, что хотите удалить $cardName?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Да") { dialog, which ->
            dialog.dismiss()
            deleteCard()
        }

        builder.setNegativeButton("Нет") { dialog, which ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun deleteCard() {
        val cardsList: ArrayList<Card> = currentBoard.taskList[taskListPosition].cards
        cardsList.removeAt(cardListPosition)

        showProgressDialog()
        viewModel.addUpdateTaskList(currentBoard) {
            hideProgressDialog()
            NAV_CONTROLLER.popBackStack()
        }
    }

    companion object {
        private const val SELECT: String = "select"
    }
}