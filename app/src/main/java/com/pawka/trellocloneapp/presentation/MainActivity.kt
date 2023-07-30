package com.pawka.trellocloneapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.pawka.trellocloneapp.R
import com.pawka.trellocloneapp.presentation.app_drawer.AppDrawer
import com.pawka.trellocloneapp.utils.APP_ACTIVITY
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    val appDrawer: AppDrawer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        APP_ACTIVITY = this
        initViews()
        setSupportActionBar(toolbar)
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar_main_activity)
    }
}