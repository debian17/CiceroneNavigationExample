package ru.debian17.cicerone.navigation.container

import android.os.Bundle
import ru.debian17.cicerone.navigation.screen.FragmentScreen
import ru.debian17.cicerone.ui.patient.PatientsFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class PatientsFragmentContainer : BaseFragmentContainer() {

    companion object {
        const val TAG = "PatientsFragmentContainerTag"
        fun newInstance(): PatientsFragmentContainer {
            return PatientsFragmentContainer()
        }
    }

    override fun initNavigator(): Navigator {
        return SupportAppNavigator(requireActivity(), childFragmentManager, containerId)
    }

    override fun getInitialFragmentScreen(params: Bundle?): FragmentScreen {
        return FragmentScreen(PatientsFragment.newInstance())
    }

}