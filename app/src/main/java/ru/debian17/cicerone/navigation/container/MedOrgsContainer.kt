package ru.debian17.cicerone.navigation.container

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.RouterProvider
import ru.debian17.cicerone.ui.TestActivity
import ru.debian17.cicerone.ui.medorg.MedOrgInfoFragment
import ru.debian17.cicerone.ui.medorg.MedOrgsFragment
import ru.debian17.cicerone.ui.medorg.SomeInfoFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportAppNavigator

class MedOrgsContainer : BaseContainer() {

    companion object {
        const val TAG = "MedOrgsContainerTag"
        fun newInstance(): MedOrgsContainer {
            return MedOrgsContainer().apply {
                arguments = Bundle().apply {
                    putString(CONTAINER_NAME_KEY, TAG)
                }
            }
        }
    }

    override fun initNavigator(): Navigator {
        return object : SupportAppNavigator(activity, childFragmentManager, R.id.base_container) {

            override fun createFragment(screenKey: String?, data: Any?): Fragment {
                return when (screenKey) {
                    MedOrgsFragment.TAG -> {
                        MedOrgsFragment.newInstance()
                    }
                    MedOrgInfoFragment.TAG -> {
                        MedOrgInfoFragment.newInstance()
                    }
                    SomeInfoFragment.TAG -> {
                        SomeInfoFragment.newInstance()
                    }
                    else -> throw RuntimeException("Unknown screenKey $screenKey")
                }
            }

            override fun createActivityIntent(context: Context, screenKey: String?, data: Any?): Intent? {
                return when (screenKey) {
                    TestActivity.TAG -> {
                        TestActivity.getStartIntent(context)
                    }
                    else -> null
                }
            }

        }
    }

    override fun firstFragmentName(): String {
        return MedOrgsFragment.TAG
    }

}