package ru.debian17.cicerone.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import ru.debian17.cicerone.navigation.container.BaseFragmentContainer
import ru.debian17.cicerone.navigation.container.FirstTabContainer
import ru.debian17.cicerone.navigation.container.SecondTabContainer
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import java.lang.RuntimeException
import java.util.*

class BottomTabNavigator(
        activity: FragmentActivity,
        @IdRes containerId: Int
) : SupportAppNavigator(activity, containerId) {

    private val containers = LinkedList<BaseFragmentContainer>()

    fun initContainers() {
        val fm = fragmentManager ?: return
        val firstTabContainer = fm.findFragmentByTag(FirstTabContainer.TAG) as? FirstTabContainer
                ?: FirstTabContainer.newInstance().apply {
                    fm.beginTransaction()
                            .replace(containerId, this, FirstTabContainer.TAG)
                            .detach(this)
                            .commitNow()
                }

        val secondTabContainer = fm.findFragmentByTag(SecondTabContainer.TAG) as? SecondTabContainer
                ?: SecondTabContainer.newInstance().apply {
                    fm.beginTransaction()
                            .replace(containerId, this, SecondTabContainer.TAG)
                            .detach(this)
                            .commitNow()
                }
        containers.add(firstTabContainer)
        containers.add(secondTabContainer)
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