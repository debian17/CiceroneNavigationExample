package ru.debian17.cicerone.navigation

import ru.debian17.cicerone.navigation.screen.FragmentScreen
import ru.terrakok.cicerone.Router

class AppRouter : Router() {

    fun replaceTab(screen: FragmentScreen) {
        val command = ReplaceBottomTab(screen)
        executeCommands(command)
    }

}