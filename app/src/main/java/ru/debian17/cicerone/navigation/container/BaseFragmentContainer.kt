package ru.debian17.cicerone.navigation.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.*
import ru.debian17.cicerone.navigation.screen.FragmentScreen
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

abstract class BaseFragmentContainer : androidx.fragment.app.Fragment(), BackButtonListener, RouterProvider {

    protected abstract fun getInitialFragmentScreen(params: Bundle?): FragmentScreen

    private lateinit var navigator: Navigator
    private val containerId = R.id.base_container

    override val router: AppRouter
        get() = getCicerone().router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this::navigator.isInitialized) {
            navigator = initNavigator()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base_container, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(containerId) == null) {
            router.newRootScreen(getInitialFragmentScreen(null))
        }
    }

    override fun onResume() {
        super.onResume()
        getCicerone().navigatorHolder.setNavigator(getNavigator())
    }

    override fun onPause() {
        super.onPause()
        getCicerone().navigatorHolder.removeNavigator()
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(containerId)
        return if (fragment != null && fragment is BackButtonListener && fragment.onBackPressed()) {
            true
        } else {
            (activity as RouterProvider).router.exit()
            true
        }
    }

    protected open fun initNavigator(): Navigator {
        return AppNavigator(requireActivity(), childFragmentManager, containerId)
    }

    fun getContainerName(): String {
        return javaClass.canonicalName ?: "Unknown container name"
    }

    private fun getCicerone(): Cicerone<AppRouter> {
        val containerName = getContainerName()
        return LocalCiceroneHolder.getCicerone(containerName)
    }

    private fun getNavigator(): Navigator {
        return navigator
    }

}