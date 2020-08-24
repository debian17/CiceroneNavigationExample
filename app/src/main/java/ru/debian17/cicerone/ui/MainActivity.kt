package ru.debian17.cicerone.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.AppRouter
import ru.debian17.cicerone.navigation.BackButtonListener
import ru.debian17.cicerone.navigation.AppNavigator
import ru.debian17.cicerone.navigation.RouterProvider
import ru.debian17.cicerone.navigation.screen.FragmentScreen
import ru.debian17.cicerone.navigation.container.FirstTabContainer
import ru.debian17.cicerone.navigation.container.SecondTabContainer
import ru.terrakok.cicerone.Cicerone

class MainActivity : AppCompatActivity(), RouterProvider {

    private val cicerone = Cicerone.create(AppRouter())
    private lateinit var appNavigator: AppNavigator

    override val router: AppRouter
        get() = cicerone.router

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_first -> {
                val screen = FragmentScreen(FirstTabContainer.newInstance())
                router.replaceTab(screen)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_second -> {
                val screen = FragmentScreen(SecondTabContainer.newInstance())
                router.replaceTab(screen)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appNavigator = AppNavigator(this, R.id.main_container)
        appNavigator.initContainers()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            val screen = FragmentScreen(FirstTabContainer.newInstance())
            router.replaceTab(screen)
        }

    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        cicerone.navigatorHolder.setNavigator(appNavigator)
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
