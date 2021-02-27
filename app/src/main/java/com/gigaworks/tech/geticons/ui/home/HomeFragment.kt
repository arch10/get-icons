package com.gigaworks.tech.geticons.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gigaworks.tech.geticons.R
import com.gigaworks.tech.geticons.databinding.FragmentHomeBinding
import com.gigaworks.tech.geticons.ui.base.BaseFragment
import com.gigaworks.tech.geticons.ui.home.adapter.TabAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = TabAdapter(requireActivity())
        binding.viewPager.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

        val tabLayoutMediator = TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.text = "Icon Sets"
                1 -> tab.text = "Icons"
            }
        }
        tabLayoutMediator.attach()

        setActionBar(binding.toolbar, getString(R.string.app_name)) {
            findNavController().navigateUp()
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

}