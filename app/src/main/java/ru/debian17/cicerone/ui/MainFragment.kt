package ru.debian17.cicerone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_main.*
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.BackButtonListener
import ru.debian17.cicerone.navigation.RouterProvider
import ru.debian17.cicerone.navigation.TabFragmentContainer
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainFragment : BaseFragment(), RouterProvider {

    companion object {
        fun newInstance() = MainFragment()

        const val TAB_FIRST = "TAB_FIRST"
        const val TAB_SECOND = "TAB_SECOND"

    }

    private val cicerone = Cicerone.create()
    override val router: Router
        get() = cicerone.router

    private lateinit var tabNavigator: SupportAppNavigator

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_first -> {
                selectTab(TAB_FIRST)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_second -> {
                selectTab(TAB_SECOND)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabNavigator = SupportAppNavigator(requireActivity(), childFragmentManager, R.id.flMainFragment)

        if (savedInstanceState == null) {
            selectTab(TAB_FIRST)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bnvTabs.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onResume() {
        super.onResume()
        cicerone.navigatorHolder.setNavigator(tabNavigator)
    }

    override fun onPause() {
        super.onPause()
        cicerone.navigatorHolder.removeNavigator()
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(R.id.flMainFragment)
        if ((fragment != null && fragment is BackButtonListener && fragment.onBackPressed())) {
            return true
        } else {
            router.exit()
            return true
        }
    }

    private fun selectTab(tabName: String) {
        val currentFragment = childFragmentManager.fragments.firstOrNull { it.isAdded }
        val newFragment = childFragmentManager.findFragmentByTag(tabName)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) {
            return
        }

        val transaction = childFragmentManager.beginTransaction()

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

}