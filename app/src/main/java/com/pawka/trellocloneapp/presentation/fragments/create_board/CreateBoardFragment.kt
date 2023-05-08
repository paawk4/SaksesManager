package com.pawka.trellocloneapp.presentation.fragments.create_board

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.presentation.fragments.BaseFragment
import com.pawka.trellocloneapp.utils.APP_ACTIVITY
import com.pawka.trellocloneapp.utils.NAV_CONTROLLER
import de.hdodenhof.circleimageview.CircleImageView

class CreateBoardFragment : BaseFragment(R.layout.fragment_create_board) {

    private lateinit var viewModel: CreateBoardViewModel

    private lateinit var createBoardBtn: Button
    private lateinit var nameBoardTil: TextInputLayout
    private lateinit var nameBoardEt: TextInputEditText
    private lateinit var imageBoardIv: CircleImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateBoardViewModel::class.java]

        configureToolbar()
        initViews(view)
        addTextChangeListener()
        observeViewModel()

        createBoardBtn.setOnClickListener {
            viewModel.createBoard(nameBoardEt.text.toString()) {
                NAV_CONTROLLER.popBackStack()
            }
        }
    }

    private fun addTextChangeListener() {
        nameBoardEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            nameBoardTil.error = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
        }
    }

    private fun initViews(view: View) {
        createBoardBtn = view.findViewById(R.id.create_board_create_btn)
        nameBoardEt = view.findViewById(R.id.create_board_name_et)
        imageBoardIv = view.findViewById(R.id.create_board_image)
        nameBoardTil = view.findViewById(R.id.create_board_name_til)
    }

    private fun configureToolbar() {
        APP_ACTIVITY.toolbar.title = "Создать доску"
        APP_ACTIVITY.appDrawer.disableDrawer()
        APP_ACTIVITY.toolbar.setNavigationIcon(R.drawable.ic_back)
    }
}