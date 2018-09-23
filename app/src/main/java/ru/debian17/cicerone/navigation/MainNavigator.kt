package ru.debian17.cicerone.navigation

import android.support.v4.app.FragmentActivity
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.container.MedOrgsContainer
import ru.debian17.cicerone.navigation.container.PatientsContainer
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

class MainNavigator(private val activity: FragmentActivity) : Navigator {

    private lateinit var medOrgsContainer: MedOrgsContainer
    private lateinit var patientContainer: PatientsContainer

    fun initContainers() {
        val fm = activity.supportFragmentManager
        medOrgsContainer = fm.findFragmentByTag(MedOrgsContainer.TAG) as? MedOrgsContainer
                ?: MedOrgsContainer.newInstance().apply {
            fm.beginTransaction()
                    .add(R.id.main_container, this, MedOrgsContainer.TAG)
                    .detach(this)
                    .commitNow()
        }

        patientContainer = fm.findFragmentByTag(PatientsContainer.TAG) as? PatientsContainer
                ?: PatientsContainer.newInstance().apply {
            fm.beginTransaction()
                    .add(R.id.main_container, this, PatientsContainer.TAG)
                    .detach(this)
                    .commitNow()
        }

    }

    override fun applyCommands(commands: Array<Command>) {
        commands.forEach {
            applyCommand(it)
        }
    }

    private fun applyCommand(command: Command) {
        when (command) {
            is Back -> {
                activity.finish()
            }

            is Forward -> {
                when (command.screenKey) {

                }
            }

            is Replace -> {
                val fm = activity.supportFragmentManager
                when (command.screenKey) {
                    MedOrgsContainer.TAG -> {
                        fm.beginTransaction()
                                .detach(patientContainer)
                                .attach(medOrgsContainer)
                                .commitNow()
                    }
                    PatientsContainer.TAG -> {
                        fm.beginTransaction()
                                .detach(medOrgsContainer)
                                .attach(patientContainer)
                                .commitNow()
                    }
                }

            }
        }
    }


}