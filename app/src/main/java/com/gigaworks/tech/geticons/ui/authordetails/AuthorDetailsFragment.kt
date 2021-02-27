package com.gigaworks.tech.geticons.ui.authordetails

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
import com.gigaworks.tech.geticons.databinding.FragmentAuthorDetailsBinding
import com.gigaworks.tech.geticons.domain.IconSet
import com.gigaworks.tech.geticons.ui.authordetails.adapter.IconSetAdapter
import com.gigaworks.tech.geticons.ui.authordetails.viewmodel.AuthorDetailsViewModel
import com.gigaworks.tech.geticons.ui.base.BaseFragment
import com.gigaworks.tech.geticons.util.Response
import com.gigaworks.tech.geticons.util.logD
import com.gigaworks.tech.geticons.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorDetailsFragment : BaseFragment<FragmentAuthorDetailsBinding>() {

    private val args: AuthorDetailsFragmentArgs by navArgs()
    private val viewModel: AuthorDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.iconSetList.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    logD("size: ${it.response.size}")
                    binding.rv.visible(true)
                    binding.notFound.visible(false)
                    val adapter = IconSetAdapter(
                        it.response,
                        object : IconSetAdapter.OnIconSetClickListener {
                            override fun onIconSetClick(icon: IconSet) {
                                val action = AuthorDetailsFragmentDirections.actionAuthorIconSetDetails(icon)
                                findNavController().navigate(action)
                            }
                        })
                    binding.rv.adapter = adapter
                }
                is Response.Failure -> {
                    logD(it.message)
                    binding.rv.visible(false)
                    binding.notFound.visible(true)
                }
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.loader.visible(it)
        }
    }

    private fun setupView() {
        val author = args.author

        with(binding) {
            name.text = author.name
            username.text = author.username
            website.text = author.website

            if(author.website.isEmpty()) {
                website.visible(false)
            } else {
                website.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(author.website)
                    })
                }
            }
        }

        viewModel.getIconSetList(author.id)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAuthorDetailsBinding.inflate(inflater, container, false)
}