package ru.debian17.cicerone.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import ru.debian17.cicerone.navigation.container.BaseFragmentContainer
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

    private val containers = ArrayList<BaseFragmentContainer>()

    fun initContainers() {
        val fm = fragmentManager ?: return
        val medOrgsContainer = fm.findFragmentByTag(MedOrgsFragmentContainer.TAG) as? MedOrgsFragmentContainer
                ?: MedOrgsFragmentContainer.newInstance().apply {
                    fm.beginTransaction()
                            .replace(containerId, this, MedOrgsFragmentContainer.TAG)
                            .detach(this)
                            .commitNow()
                }

        val patientContainer = fm.findFragmentByTag(PatientsFragmentContainer.TAG) as? PatientsFragmentContainer
                ?: PatientsFragmentContainer.newInstance().apply {
                    fm.beginTransaction()
                            .replace(containerId, this, PatientsFragmentContainer.TAG)
                            .detach(this)
                            .commitNow()
                }
        containers.add(medOrgsContainer)
        containers.add(patientContainer)
    }

    override fun applyCommand(command: Command) {
        if (command is Replace) {
            val transaction = fragmentManager?.beginTransaction() ?: return
            var wasContainerAttached = false
            containers.forEach { container ->
                if (container.getContainerName() == command.screen.screenKey) {
                    transaction.attach(container)
                    wasContainerAttached = true
                } else {
                    transaction.detach(container)
                }
            }
            if (!wasContainerAttached) {
                throw RuntimeException("Container = ${command.screen.screenKey} not found!")
            }
            transaction.commitNow()
        } else {
            super.applyCommand(command)
        }
    }

}