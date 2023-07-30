package com.pawka.trellocloneapp

import android.app.Application
import com.pawka.trellocloneapp.di.repositoriesModule
import com.pawka.trellocloneapp.di.useCasesModule
import com.pawka.trellocloneapp.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SaksesManagerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SaksesManagerApp)
            modules(listOf(repositoriesModule, viewModelsModule, useCasesModule))
        }
    }
}