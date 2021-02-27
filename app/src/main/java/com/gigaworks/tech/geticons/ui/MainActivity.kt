package com.gigaworks.tech.geticons.ui

import android.view.LayoutInflater
import com.gigaworks.tech.geticons.databinding.ActivityMainBinding
import com.gigaworks.tech.geticons.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(inflater)
}