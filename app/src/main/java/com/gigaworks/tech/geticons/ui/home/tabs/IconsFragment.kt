package com.gigaworks.tech.geticons.ui.home.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.gigaworks.tech.geticons.databinding.FragmentIconsBinding
import com.gigaworks.tech.geticons.domain.Icon
import com.gigaworks.tech.geticons.ui.base.BaseFragment
import com.gigaworks.tech.geticons.ui.home.adapter.IconAdapter
import com.gigaworks.tech.geticons.ui.home.adapter.LoaderStateAdapter
import com.gigaworks.tech.geticons.ui.home.viewmodel.IconsViewModel
import com.gigaworks.tech.geticons.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IconsFragment : BaseFragment<FragmentIconsBinding>() {

    private val viewModel: IconsViewModel by viewModels()

    private val adapter = IconAdapter(object : IconAdapter.OnIconSetClick {
        override fun onIconSetClick(icon: Icon) {
            Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.iconList.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun setupView() {
        binding.rv.adapter = adapter.withLoadStateFooter(
            footer = LoaderStateAdapter()
        )
        adapter.addLoadStateListener {
            with(binding) {
                loader.visible(it.source.refresh is LoadState.Loading)
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentIconsBinding.inflate(inflater, container, false)
}