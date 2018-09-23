package ru.debian17.cicerone

import android.app.Application
import ru.debian17.cicerone.di.component.AppComponent
import ru.debian17.cicerone.di.component.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()
    }

}