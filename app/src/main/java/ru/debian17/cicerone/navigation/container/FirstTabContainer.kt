package ru.debian17.cicerone.navigation.container

import android.os.Bundle
import ru.debian17.cicerone.navigation.screen.FragmentScreen
import ru.debian17.cicerone.ui.first_tab.FirstTabFirstFragment

class FirstTabContainer : BaseFragmentContainer() {

    companion object {
        const val TAG = "FirstTabContainerTag"
        fun newInstance(): FirstTabContainer {
            return FirstTabContainer()
        }
    }

    override fun getInitialFragmentScreen(params: Bundle?): FragmentScreen {
        return FragmentScreen(FirstTabFirstFragment.newInstance())
    }

}