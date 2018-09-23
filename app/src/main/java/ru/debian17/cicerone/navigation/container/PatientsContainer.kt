package ru.debian17.cicerone.navigation.container

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import ru.debian17.cicerone.R
import ru.debian17.cicerone.ui.patient.PatientInfoFragment
import ru.debian17.cicerone.ui.patient.PatientsFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportAppNavigator

class PatientsContainer : BaseContainer() {

    companion object {
        const val TAG = "PatientsContainerTag"
        fun newInstance(): PatientsContainer {
            return PatientsContainer().apply {
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
                    PatientsFragment.TAG -> {
                        PatientsFragment.newInstance()
                    }
                    PatientInfoFragment.TAG -> {
                        PatientInfoFragment.newInstance()
                    }
                    else -> throw RuntimeException("Unknown screenKey $screenKey")
                }
            }

            override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? {
                return null
            }

        }
    }

    override fun firstFragmentName(): String {
        return PatientsFragment.TAG
    }
}