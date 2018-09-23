package ru.debian17.cicerone.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.debian17.cicerone.App
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.BackButtonListener
import ru.debian17.cicerone.navigation.MainNavigator
import ru.debian17.cicerone.navigation.RouterProvider
import ru.debian17.cicerone.navigation.container.MedOrgsContainer
import ru.debian17.cicerone.navigation.container.PatientsContainer
import ru.debian17.cicerone.ui.medorg.MedOrgsFragment
import ru.debian17.cicerone.ui.patient.PatientsFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivity : AppCompatActivity(), RouterProvider {

    private lateinit var mainNavigator: MainNavigator

    @Inject
    override lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                router.replaceScreen(MedOrgsContainer.TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                router.replaceScreen(PatientsContainer.TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainNavigator = MainNavigator(this)
        mainNavigator.initContainers()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            router.replaceScreen(MedOrgsContainer.TAG)
        }

    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(mainNavigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_container)
        if ((fragment != null && fragment is BackButtonListener && fragment.onBackPressed())) {
            return
        } else {
            router.exit()
        }
    }

}
