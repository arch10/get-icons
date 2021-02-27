package com.gigaworks.tech.geticons.ui.iconsetdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gigaworks.tech.geticons.databinding.FragmentIconSetDetailsBinding
import com.gigaworks.tech.geticons.domain.Author
import com.gigaworks.tech.geticons.domain.Icon
import com.gigaworks.tech.geticons.ui.base.BaseFragment
import com.gigaworks.tech.geticons.ui.iconsetdetails.adapter.IconAdapter
import com.gigaworks.tech.geticons.ui.iconsetdetails.viewmodel.IconSetDetailsViewModel
import com.gigaworks.tech.geticons.util.Response
import com.gigaworks.tech.geticons.util.logD
import com.gigaworks.tech.geticons.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IconSetDetailsFragment : BaseFragment<FragmentIconSetDetailsBinding>() {

    private val args: IconSetDetailsFragmentArgs by navArgs()
    private val viewModel: IconSetDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupObservables()
    }

    private fun setupView() {

        setActionBar(binding.toolbar, "Icon Set Details") {
            findNavController().navigateUp()
        }

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

            if(iconSet.readme.isEmpty()) {
                readme.text = "N/A"
            }

            if(iconSet.authorWebsite.isNullOrEmpty()) {
                website.visible(false)
            } else {
                website.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(iconSet.authorWebsite)
                    })
                }
            }

            author.setOnClickListener {
                if(iconSet.authorId != null) {
                    val author = Author(
                        name = iconSet.authorName?:"",
                        website = iconSet.authorWebsite?:"",
                        id = iconSet.authorId!!,
                        username = iconSet.authorUsername?:""
                    )
                    val action = IconSetDetailsFragmentDirections.iconSetShowAuthor(author)
                    findNavController().navigate(action)
                }
            }
        }
        viewModel.getIconList(iconSet.id)

        binding.rv.isNestedScrollingEnabled = false

    }

    private fun setupObservables() {
        viewModel.iconList.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    val iconList = it.response.map { icon ->
                        val iconSet = args.iconSet
                        icon.apply {
                            readme = iconSet.readme
                            license = iconSet.license
                            authorName = iconSet.authorName
                            authorWebsite = iconSet.authorWebsite
                            authorUsername = iconSet.authorWebsite
                            authorId = iconSet.authorId
                        }
                    }
                    val adapter = IconAdapter(iconList, object : IconAdapter.OnIconClickListener {
                        override fun onIconClick(icon: Icon) {
                            val action = IconSetDetailsFragmentDirections.showIconDetails(icon)
                            findNavController().navigate(action)
                        }
                    })
                    binding.rv.adapter = adapter
                }
                is Response.Failure -> {
                    logD(it.message)
                }
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.loader.visible(it)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentIconSetDetailsBinding.inflate(inflater, container, false)
}