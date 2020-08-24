package ru.debian17.cicerone.navigation.screen

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class FragmentScreen(private val fragment: Fragment) : SupportAppScreen() {

    override fun getFragment(): Fragment? {
        return fragment
    }

}