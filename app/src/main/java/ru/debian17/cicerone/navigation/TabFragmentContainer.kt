package ru.debian17.cicerone.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.screen.FragmentScreen
import ru.debian17.cicerone.ui.BaseFragment
import ru.debian17.cicerone.ui.MainActivity
import ru.debian17.cicerone.ui.MainFragment
import ru.debian17.cicerone.ui.first_tab.FirstTabFirstFragment
import ru.debian17.cicerone.ui.second_tab.SecondTabFirstFragment

class TabFragmentContainer : BaseFragment() {

    companion object {
        private const val TAB_NAME = "TAB_NAME"
        fun newInstance(tabName: String): TabFragmentContainer {
            val args = Bundle()
            args.putString(TAB_NAME, tabName)
            val fragment = TabFragmentContainer()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var globalNavigator: GlobalNavigator

    private fun getInitialFragmentScreen(tabName: String): FragmentScreen {
        val fragment: Fragment = when (tabName) {
            MainFragment.TAB_FIRST -> FirstTabFirstFragment.newInstance()
            MainFragment.TAB_SECOND -> SecondTabFirstFragment.newInstance()
            else -> throw RuntimeException("Unknown tab!")
        }
        return FragmentScreen(fragment)
    }

    private val containerId = R.id.base_container

    private val tabName: String by lazy {
        arguments!!.getString(TAB_NAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        globalNavigator = (activity as MainActivity).globalNavigator
        globalNavigator.initTab(tabName, childFragmentManager, containerId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base_container, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(containerId) == null) {
            val initialScreen = getInitialFragmentScreen(tabName)
            globalNavigator.getCurrentTab().newRootScreen(initialScreen)
        }
    }

    override fun onResume() {
        super.onResume()
        globalNavigator.setTabNavigator(tabName)
    }

    override fun onPause() {
        super.onPause()
        globalNavigator.removeTabNavigator(tabName)
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(containerId)
        if (fragment != null && fragment is BackButtonListener && fragment.onBackPressed()) {
            return true
        } else {
            val act = (activity as? RouterProvider) ?: return false
            act.router?.exit()
            return true
        }
    }

}