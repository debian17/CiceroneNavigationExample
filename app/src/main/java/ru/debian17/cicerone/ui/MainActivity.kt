package ru.debian17.cicerone.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.BackButtonListener
import ru.debian17.cicerone.navigation.GlobalNavigator
import ru.debian17.cicerone.navigation.screen.FragmentScreen

class MainActivity : AppCompatActivity() {

    val globalNavigator = GlobalNavigator(this, R.id.flMain)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val screen = FragmentScreen(MainFragment.newInstance())
            globalNavigator.fullScreen.newRootScreen(screen)
        }

    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        globalNavigator.setMainNavigator()
    }

    override fun onPause() {
        super.onPause()
        globalNavigator.removeMainNavigator()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.flMain)
        if ((fragment != null && fragment is BackButtonListener && fragment.onBackPressed())) {
            return
        } else {
            globalNavigator.fullScreen.exit()
        }
    }

}
