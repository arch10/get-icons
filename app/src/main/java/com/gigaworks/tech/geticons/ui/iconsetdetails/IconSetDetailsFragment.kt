package com.gigaworks.tech.geticons.ui.iconsetdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.gigaworks.tech.geticons.databinding.FragmentIconSetDetailsBinding
import com.gigaworks.tech.geticons.domain.Icon
import com.gigaworks.tech.geticons.ui.base.BaseFragment
import com.gigaworks.tech.geticons.ui.home.adapter.LoaderStateAdapter
import com.gigaworks.tech.geticons.ui.iconsetdetails.adapter.IconAdapter
import com.gigaworks.tech.geticons.ui.iconsetdetails.viewmodel.IconSetDetailsViewModel
import com.gigaworks.tech.geticons.util.logD
import com.gigaworks.tech.geticons.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IconSetDetailsFragment : BaseFragment<FragmentIconSetDetailsBinding>() {

    private val args: IconSetDetailsFragmentArgs by navArgs()
    private val viewModel: IconSetDetailsViewModel by viewModels()

    private val adapter = IconAdapter(object: IconAdapter.OnIconClickListener {
        override fun onIconClick(icon: Icon) {
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
//            val action = HomeFragmentDirections.showIconSetDetails(iconSet)
//            findNavController().navigate(action)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupObservables()
    }

    private fun setupView() {
        val iconSet = args.iconSet
        with(binding) {
            name.text = iconSet.name
            author.text = iconSet.authorName
            type.text = iconSet.type
            license.text = iconSet.license
            price.text = iconSet.price
            readme.text = iconSet.readme
            website.text = iconSet.authorWebsite
            premium.visible(iconSet.isPremium)

            if(iconSet.authorWebsite.isNotEmpty()) {
                website.setOnClickListener{
                    startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(iconSet.authorWebsite)
                    })
                }
            }
        }
        viewModel.getIconList(iconSet.id)

        binding.rv.setHasFixedSize(true)
        binding.rv.isNestedScrollingEnabled = false
        binding.rv.adapter = adapter.withLoadStateFooter(
            footer = LoaderStateAdapter()
        )

//        adapter.addLoadStateListener {
//            with(binding) {
//                loader.visible(it.source.refresh is LoadState.Loading)
//            }
//        }
    }

    private fun setupObservables() {
        viewModel.iconList.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentIconSetDetailsBinding.inflate(inflater, container, false)
}