package com.gigaworks.tech.geticons.ui.home.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gigaworks.tech.geticons.databinding.FragmentIconSetBinding
import com.gigaworks.tech.geticons.domain.IconSet
import com.gigaworks.tech.geticons.ui.base.BaseFragment
import com.gigaworks.tech.geticons.ui.home.HomeFragmentDirections
import com.gigaworks.tech.geticons.ui.home.adapter.IconSetAdapter
import com.gigaworks.tech.geticons.ui.home.adapter.LoaderStateAdapter
import com.gigaworks.tech.geticons.ui.home.viewmodel.IconSetViewModel
import com.gigaworks.tech.geticons.ui.iconsetdetails.IconSetDetailsFragmentArgs
import com.gigaworks.tech.geticons.util.Response
import com.gigaworks.tech.geticons.util.logD
import com.gigaworks.tech.geticons.util.visible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IconSetFragment : BaseFragment<FragmentIconSetBinding>() {

    private val viewModel: IconSetViewModel by viewModels()
    private val adapter = IconSetAdapter(object: IconSetAdapter.OnIconSetClick {
        override fun onIconSetClick(iconSet: IconSet) {
            val action = HomeFragmentDirections.showIconSetDetails(iconSet)
            findNavController().navigate(action)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservables()
        setupView()
    }

    private fun setupView() {

        binding.rv.setHasFixedSize(true)
        binding.rv.adapter = adapter.withLoadStateFooter(
            footer = LoaderStateAdapter()
        )

        adapter.addLoadStateListener {
            with(binding) {
                loader.visible(it.source.refresh is LoadState.Loading)
            }
        }

    }

    private fun setupObservables() {
        viewModel.iconSetList.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentIconSetBinding.inflate(inflater, container, false)
}