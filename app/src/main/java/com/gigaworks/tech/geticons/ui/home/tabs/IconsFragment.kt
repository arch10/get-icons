package com.gigaworks.tech.geticons.ui.home.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gigaworks.tech.geticons.databinding.FragmentIconsBinding
import com.gigaworks.tech.geticons.ui.base.BaseFragment

class IconsFragment : BaseFragment<FragmentIconsBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentIconsBinding.inflate(inflater, container, false)
}