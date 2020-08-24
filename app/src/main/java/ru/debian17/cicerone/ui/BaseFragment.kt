package ru.debian17.cicerone.ui

import androidx.fragment.app.Fragment
import ru.debian17.cicerone.navigation.BackButtonListener
import ru.debian17.cicerone.navigation.RouterProvider

abstract class BaseFragment : Fragment(), BackButtonListener {

    override fun onBackPressed(): Boolean {
        val pf = (parentFragment as? RouterProvider) ?: return false
        pf.router.exit()
        return true
    }

}