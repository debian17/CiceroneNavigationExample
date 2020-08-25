package ru.debian17.cicerone.ui.second_tab


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_second_tab_second.*
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.RouterProvider
import ru.debian17.cicerone.navigation.screen.ActivityScreen
import ru.debian17.cicerone.ui.BaseFragment
import ru.debian17.cicerone.ui.MainActivity
import ru.debian17.cicerone.ui.TestActivity

class SecondTabSecondFragment : BaseFragment() {

    companion object {
        fun newInstance(): SecondTabSecondFragment {
            return SecondTabSecondFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second_tab_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnStartActivity.setOnClickListener {
            val screen = ActivityScreen(TestActivity.getStartIntent(requireContext()))
            (activity as MainActivity).globalNavigator.getCurrentTab().openActivity(screen)
        }
    }

    override fun onBackPressed(): Boolean {
        (activity as MainActivity).globalNavigator.getCurrentTab().exit()
        return true
    }

}
