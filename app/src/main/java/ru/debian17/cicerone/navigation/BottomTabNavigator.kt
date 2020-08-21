package ru.debian17.cicerone.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import ru.debian17.cicerone.navigation.container.MedOrgsFragmentContainer
import ru.debian17.cicerone.navigation.container.PatientsFragmentContainer
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import java.lang.RuntimeException

class BottomTabNavigator(
        activity: FragmentActivity,
        @IdRes containerId: Int
) : SupportAppNavigator(activity, containerId) {

    private lateinit var medOrgsContainer: MedOrgsFragmentContainer
    private lateinit var patientContainer: PatientsFragmentContainer

    fun initContainers() {
        val fm = fragmentManager ?: return
        medOrgsContainer = fm.findFragmentByTag(MedOrgsFragmentContainer.TAG) as? MedOrgsFragmentContainer
                ?: MedOrgsFragmentContainer.newInstance().apply {
                    fm.beginTransaction()
                            .replace(containerId, this, MedOrgsFragmentContainer.TAG)
                            .detach(this)
                            .commitNow()
                }

        patientContainer = fm.findFragmentByTag(PatientsFragmentContainer.TAG) as? PatientsFragmentContainer
                ?: PatientsFragmentContainer.newInstance().apply {
                    fm.beginTransaction()
                            .replace(containerId, this, PatientsFragmentContainer.TAG)
                            .detach(this)
                            .commitNow()
                }
    }

    override fun applyCommand(command: Command) {
        if (command is Replace) {
            val fm = fragmentManager ?: return
            when (command.screen.screenKey) {
                medOrgsContainer.javaClass.canonicalName -> {
                    fm.beginTransaction()
                            .detach(patientContainer)
                            .attach(medOrgsContainer)
                            .commitNow()
                }
                patientContainer.javaClass.canonicalName -> {
                    fm.beginTransaction()
                            .detach(medOrgsContainer)
                            .attach(patientContainer)
                            .commitNow()
                }
                else -> {
                    throw RuntimeException("Unknown container!")
                }
            }
        } else {
            super.applyCommand(command)
        }
    }

}