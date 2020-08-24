package ru.debian17.cicerone.ui.first_tab


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_first_tab_first.*
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.RouterProvider
import ru.debian17.cicerone.navigation.screen.FragmentScreen
import ru.debian17.cicerone.ui.BaseFragment

class FirstTabFirstFragment : BaseFragment() {

    companion object {
        fun newInstance(): FirstTabFirstFragment {
            return FirstTabFirstFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first_tab_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSecond.setOnClickListener {
            val screen = FragmentScreen(FirstTabSecondFragment.newInstance())
            (parentFragment as RouterProvider).router.navigateTo(screen)
        }
    }


}
