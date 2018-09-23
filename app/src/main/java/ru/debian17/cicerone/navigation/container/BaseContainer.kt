package ru.debian17.cicerone.navigation.container

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.debian17.cicerone.App
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.BackButtonListener
import ru.debian17.cicerone.navigation.LocalCiceroneHolder
import ru.debian17.cicerone.navigation.RouterProvider
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class BaseContainer : Fragment(), BackButtonListener, RouterProvider {

    companion object {
        const val CONTAINER_NAME_KEY = "baseContainerNameKey"
    }

    protected abstract fun initNavigator(): Navigator
    protected abstract fun firstFragmentName(): String

    @Inject
    lateinit var ciceroneHolder: LocalCiceroneHolder

    private lateinit var navigator: Navigator
    private lateinit var containerName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        if (!this::navigator.isInitialized) {
            navigator = initNavigator()
        }

        arguments?.let {
            containerName = it.getString(CONTAINER_NAME_KEY)
        } ?: run {
            throw RuntimeException("You need to define container name in arguments!")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base_container, container, false)
    }

    private fun getCicerone(): Cicerone<Router> {
        return ciceroneHolder.getCicerone(containerName)
    }

    protected fun getNavigator(): Navigator {
        return navigator
    }

    override val router: Router
        get() = getCicerone().router

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.base_container) == null) {
            getCicerone().router.replaceScreen(firstFragmentName())
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
        val fragment = childFragmentManager.findFragmentById(R.id.base_container)
        return if (fragment != null && fragment is BackButtonListener && fragment.onBackPressed()) {
            true
        } else {
            (activity as RouterProvider).router.exit()
            true
        }
    }
}