package ru.debian17.cicerone.navigation.container

import android.os.Bundle
import ru.debian17.cicerone.navigation.screen.FragmentScreen
import ru.debian17.cicerone.ui.medorg.MedOrgsFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MedOrgsFragmentContainer : BaseFragmentContainer() {

    companion object {
        const val TAG = "MedOrgsFragmentContainerTag"
        fun newInstance(): MedOrgsFragmentContainer {
            return MedOrgsFragmentContainer()
        }
    }

    override fun getInitialFragmentScreen(params: Bundle?): FragmentScreen {
        return FragmentScreen(MedOrgsFragment.newInstance())
    }

}