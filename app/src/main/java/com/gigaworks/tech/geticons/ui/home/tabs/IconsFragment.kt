package com.gigaworks.tech.geticons.ui.home.tabs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.gigaworks.tech.geticons.databinding.FragmentIconsBinding
import com.gigaworks.tech.geticons.domain.Icon
import com.gigaworks.tech.geticons.ui.base.BaseFragment
import com.gigaworks.tech.geticons.ui.home.HomeFragmentDirections
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
            val action = HomeFragmentDirections.actionSearchIconDetails(icon)
            findNavController().navigate(action)
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
        binding.editText.setOnEditorActionListener { v, actionId, _ ->
            hideKeyboard()
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchQuery = v.text.toString().trim()
                viewModel.setQuery(searchQuery)
                true
            } else {
                false
            }
        }
    }

    private fun hideKeyboard() {
        val view: View = binding.root
        val imm: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentIconsBinding.inflate(inflater, container, false)
}