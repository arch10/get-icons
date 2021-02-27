package com.gigaworks.tech.geticons.ui.authordetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.gigaworks.tech.geticons.databinding.FragmentAuthorDetailsBinding
import com.gigaworks.tech.geticons.ui.base.BaseFragment
import com.gigaworks.tech.geticons.util.visible

class AuthorDetailsFragment : BaseFragment<FragmentAuthorDetailsBinding>() {

    private val args: AuthorDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
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
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAuthorDetailsBinding.inflate(inflater, container, false)
}