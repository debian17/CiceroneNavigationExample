package ru.debian17.cicerone.di.component

import dagger.Component
import ru.debian17.cicerone.di.module.LocalNavigationModule
import ru.debian17.cicerone.di.module.NavigationModule
import ru.debian17.cicerone.navigation.container.BaseContainer
import ru.debian17.cicerone.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [NavigationModule::class, LocalNavigationModule::class])
interface AppComponent {

    fun inject(fragment: BaseContainer)

    fun inject(mainActivity: MainActivity)

}