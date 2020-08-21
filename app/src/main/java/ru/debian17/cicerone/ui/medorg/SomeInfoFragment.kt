package ru.debian17.cicerone.ui.medorg


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_some_info.*

import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.BackButtonListener
import ru.debian17.cicerone.navigation.RouterProvider
import ru.debian17.cicerone.navigation.screen.ActivityScreen
import ru.debian17.cicerone.ui.TestActivity

class SomeInfoFragment : androidx.fragment.app.Fragment(), BackButtonListener {

    companion object {
        fun newInstance(): SomeInfoFragment {
            return SomeInfoFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_some_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnStartActivity.setOnClickListener {
            (parentFragment as RouterProvider).router.navigateTo(
                    ActivityScreen(TestActivity.getStartIntent(requireContext()))
            )
        }
    }

    override fun onBackPressed(): Boolean {
        (parentFragment as RouterProvider).router.exit()
        return true
    }

}
