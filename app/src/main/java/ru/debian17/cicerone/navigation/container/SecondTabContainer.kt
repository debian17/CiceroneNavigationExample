package ru.debian17.cicerone.navigation.container

import android.os.Bundle
import ru.debian17.cicerone.navigation.screen.FragmentScreen
import ru.debian17.cicerone.ui.second_tab.SecondTabFirstFragment

class SecondTabContainer : BaseFragmentContainer() {

    companion object {
        const val TAG = "SecondTabContainerTab"
        fun newInstance(): SecondTabContainer {
            return SecondTabContainer()
        }
    }

    override fun getInitialFragmentScreen(params: Bundle?): FragmentScreen {
        return FragmentScreen(SecondTabFirstFragment.newInstance())
    }

}