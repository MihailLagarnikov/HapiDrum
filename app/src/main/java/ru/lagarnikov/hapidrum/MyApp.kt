package ru.lagarnikov.hapidrum

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        startKoin(this, listOf<Module>(appModule))
    }
}