package ru.debian17.cicerone.ui.first_tab


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_first_tab_second.*
import ru.debian17.cicerone.R
import ru.debian17.cicerone.navigation.screen.FragmentScreen
import ru.debian17.cicerone.ui.BaseFragment
import ru.debian17.cicerone.ui.MainActivity

class FirstTabSecondFragment : BaseFragment() {

    companion object {
        fun newInstance(): FirstTabSecondFragment {
            return FirstTabSecondFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first_tab_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnThird.setOnClickListener {
            val screen = FragmentScreen(FirstTabThirdFragment.newInstance())
            (activity as MainActivity).globalNavigator.getCurrentTab().navigateTo(screen)
        }
    }

    override fun onBackPressed(): Boolean {
        (activity as MainActivity).globalNavigator.getCurrentTab().exit()
        return true
    }

}
