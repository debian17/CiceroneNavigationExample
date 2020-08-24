package ru.debian17.cicerone.ui.second_tab


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_second_tab_first.*

import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.RouterProvider
import ru.debian17.cicerone.navigation.screen.FragmentScreen

class SecondTabFirstFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance(): SecondTabFirstFragment {
            return SecondTabFirstFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second_tab_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSecond.setOnClickListener {
            val screen = FragmentScreen(SecondTabSecondFragment.newInstance())
            (parentFragment as RouterProvider).router.navigateTo(screen)
        }
    }

}
