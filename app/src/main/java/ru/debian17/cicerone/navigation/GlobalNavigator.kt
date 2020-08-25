package ru.debian17.cicerone.navigation

import androidx.collection.ArrayMap
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_base_container.view.*
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.screen.ActivityScreen
import ru.debian17.cicerone.navigation.screen.FragmentScreen
import ru.debian17.cicerone.ui.MainActivity
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import java.lang.RuntimeException

class GlobalNavigator(
        private val activity: MainActivity,
        mainContainerId: Int
) {

    private val mainCicerone = Cicerone.create()
    private val mainNavigator = SupportAppNavigator(activity, mainContainerId)

    private var currentTabName = ""

    val fullScreen = ScreenNavigator(mainCicerone)

    //key tab name
    private val cicerones = ArrayMap<String, Cicerone<Router>>()
    private val navigators = ArrayMap<String, SupportAppNavigator>()
    private val screenNavigators = ArrayMap<String, ScreenNavigator>()

    fun initTab(tabName: String, fragmentManager: FragmentManager, containerId: Int) {
        if (!navigators.containsKey(tabName) && !cicerones.containsKey(tabName) && !screenNavigators.containsKey(tabName)) {
            navigators[tabName] = SupportAppNavigator(activity, fragmentManager, containerId)
            val cicerone = Cicerone.create()
            cicerones[tabName] = cicerone
            screenNavigators[tabName] = ScreenNavigator(cicerone)
        }
    }

    fun setTabNavigator(tabName: String) {
        val cicerone = cicerones[tabName] ?: throw RuntimeException("Unknown tab name!")
        val navigator = navigators[tabName] ?: throw RuntimeException("Unknown tab name!")
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    fun removeTabNavigator(tabName: String) {
        val cicerone = cicerones[tabName] ?: throw RuntimeException("Unknown tab name!")
        cicerone.navigatorHolder.removeNavigator()
    }

    fun setMainNavigator() {
        mainCicerone.navigatorHolder.setNavigator(mainNavigator)
    }

    fun removeMainNavigator() {
        mainCicerone.navigatorHolder.removeNavigator()
    }

    fun getTabScreen(tabName: String): ScreenNavigator {
        return screenNavigators[tabName] ?: throw RuntimeException("Unknown tab name!")
    }

    fun selectTab(tabName: String, fragmentManager: FragmentManager) {
        currentTabName = tabName
        val currentFragment = fragmentManager.fragments.firstOrNull { it.isAdded }
        val newFragment = fragmentManager.findFragmentByTag(tabName)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) {
            return
        }

        val transaction = fragmentManager.beginTransaction()

        if (newFragment == null) {
            transaction.add(R.id.flMainFragment, TabFragmentContainer.newInstance(tabName), tabName)
        } else {
            transaction.attach(newFragment)
        }

        if (currentFragment != null) {
            transaction.detach(currentFragment)
        }

        transaction.commitNow()
    }

    fun getCurrentTab(): ScreenNavigator {
        return screenNavigators[currentTabName] ?: throw RuntimeException("Unknown tab name!")
    }

    class ScreenNavigator(private val cicerone: Cicerone<Router>) {

        fun newRootScreen(screen: FragmentScreen) {
            cicerone.router.newRootScreen(screen)
        }

        fun navigateTo(fragmentScreen: FragmentScreen) {
            cicerone.router.navigateTo(fragmentScreen)
        }

        fun openActivity(screen: ActivityScreen) {
            cicerone.router.navigateTo(screen)
        }

        fun exit() {
            cicerone.router.exit()
        }

    }

}