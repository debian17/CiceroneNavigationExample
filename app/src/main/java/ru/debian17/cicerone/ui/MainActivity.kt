package ru.debian17.cicerone.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.BackButtonListener
import ru.debian17.cicerone.navigation.RouterProvider
import ru.debian17.cicerone.navigation.screen.FragmentScreen
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity(), RouterProvider {

    private val cicerone = Cicerone.create()
    private lateinit var appNavigator: SupportAppNavigator

    override val router: Router
        get() = cicerone.router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appNavigator = SupportAppNavigator(this, R.id.flMain)
        if (savedInstanceState == null) {
            val screen = FragmentScreen(MainFragment.newInstance())
            router.replaceScreen(screen)
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
        val fragment = supportFragmentManager.findFragmentById(R.id.flMain)
        if ((fragment != null && fragment is BackButtonListener && fragment.onBackPressed())) {
            return
        } else {
            router.exit()
        }
    }

}
