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

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()

        const val TAB_FIRST = "TAB_FIRST"
        const val TAB_SECOND = "TAB_SECOND"

    }

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

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(R.id.flMainFragment)
        if ((fragment != null && fragment is BackButtonListener && fragment.onBackPressed())) {
            return true
        } else {
            //router.exit()
            return true
        }
    }

    private fun selectTab(tabName: String) {
        (activity as MainActivity).globalNavigator.selectTab(tabName, childFragmentManager)
    }

}