package ru.debian17.cicerone.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.BackButtonListener
import ru.debian17.cicerone.navigation.BottomTabNavigator
import ru.debian17.cicerone.navigation.RouterProvider
import ru.debian17.cicerone.navigation.screen.FragmentScreen
import ru.debian17.cicerone.navigation.container.MedOrgsFragmentContainer
import ru.debian17.cicerone.navigation.container.PatientsFragmentContainer
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class MainActivity : AppCompatActivity(), RouterProvider {

    private val cicerone = Cicerone.create()
    private lateinit var bottomTabNavigator: BottomTabNavigator

    override val router: Router
        get() = cicerone.router

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                router.replaceScreen(FragmentScreen(MedOrgsFragmentContainer.newInstance()))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                router.replaceScreen(FragmentScreen(PatientsFragmentContainer.newInstance()))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomTabNavigator = BottomTabNavigator(this, R.id.main_container)
        bottomTabNavigator.initContainers()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            router.replaceScreen(FragmentScreen(MedOrgsFragmentContainer.newInstance()))
        }

    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        cicerone.navigatorHolder.setNavigator(bottomTabNavigator)
    }

    override fun onPause() {
        super.onPause()
        cicerone.navigatorHolder.removeNavigator()
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
