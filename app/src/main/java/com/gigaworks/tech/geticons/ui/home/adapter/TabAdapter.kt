package com.gigaworks.tech.geticons.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gigaworks.tech.geticons.ui.home.tabs.IconSetFragment
import com.gigaworks.tech.geticons.ui.home.tabs.IconsFragment

class TabAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            IconSetFragment()
        } else {
            IconsFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}
