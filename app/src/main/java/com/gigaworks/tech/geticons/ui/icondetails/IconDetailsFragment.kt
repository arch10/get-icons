package com.gigaworks.tech.geticons.ui.icondetails

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
import com.gigaworks.tech.geticons.domain.Author
import com.gigaworks.tech.geticons.ui.base.BaseFragment
import com.gigaworks.tech.geticons.util.visible
import com.google.android.material.snackbar.Snackbar
import java.io.File


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
            author.text = icon.authorName
            website.text = icon.authorWebsite
            readme.text = icon.readme
            license.text = icon.license

            if (icon.authorWebsite.isNullOrEmpty()) {
                website.visible(false)
            } else {
                website.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(icon.authorWebsite)
                    })
                }
            }

            if (icon.readme.isNullOrEmpty()) {
                readme.text = "N/A"
            }

            author.setOnClickListener {
                val author = Author(
                    name = icon.authorName!!,
                    website = icon.authorWebsite ?: "",
                    id = icon.authorId!!,
                    username = icon.authorUsername ?: ""
                )
                val action = IconDetailsFragmentDirections.iconDetailsShowAuthor(author)
                findNavController().navigate(action)
            }

            if (icon.imgUrl.isNotEmpty()) {
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

            download.setOnClickListener {
                if(icon.imgUrl.isNotEmpty()) {
                    downloadImageNew(icon.imgUrl)
                } else {
                    Snackbar.make(binding.root, "Invalid download URL", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun downloadImageNew(downloadUrlOfImage: String) {
        try {
            val filename = System.currentTimeMillis().toString()
            val dm = (activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager)
            val downloadUri = Uri.parse(downloadUrlOfImage)
            val request = DownloadManager.Request(downloadUri)
                .addRequestHeader("Authorization", "Bearer $AUTH_TOKEN")
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator.toString() + filename + ".jpg"
                )
            dm.enqueue(request)
            Toast.makeText(context, "Image download started.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Image download failed.", Toast.LENGTH_SHORT).show()
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