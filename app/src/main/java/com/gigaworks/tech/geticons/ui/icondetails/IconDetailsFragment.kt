package com.gigaworks.tech.geticons.ui.icondetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.gigaworks.tech.geticons.BuildConfig
import com.gigaworks.tech.geticons.R
import com.gigaworks.tech.geticons.databinding.FragmentIconDetailsBinding
import com.gigaworks.tech.geticons.ui.base.BaseFragment
import com.gigaworks.tech.geticons.util.visible

class IconDetailsFragment : BaseFragment<FragmentIconDetailsBinding>() {

    private val args: IconDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        val icon = args.icon
        with(binding) {
            name.text = icon.name
            type.text = icon.type
            premium.visible(icon.isPremium)
            download.visible(!icon.isPremium)
            price.text = icon.price
            author.text = icon.author?.name
            website.text = icon.author?.website
            readme.text = icon.readme
            license.text = icon.license

            if(icon.author?.website.isNullOrEmpty()) {
                website.visible(false)
            } else {
                website.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(icon.author?.website)
                    })
                }
            }

            if(icon.readme.isNullOrEmpty()) {
                readme.text = "N/A"
            }

            author.setOnClickListener {
                val action = IconDetailsFragmentDirections.iconDetailsShowAuthor(icon.author!!)
                findNavController().navigate(action)
            }

            val glideUrl = GlideUrl(
                icon.imgUrl,
                LazyHeaders.Builder()
                    .addHeader("Authorization", "Bearer $AUTH_TOKEN")
                    .build()
            )
            Glide.with(binding.root)
                .load(glideUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(binding.iconImg)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentIconDetailsBinding.inflate(inflater, container, false)

    companion object {
        private const val AUTH_TOKEN = BuildConfig.ACCESS_TOKEN
    }
}