package com.gigaworks.tech.geticons.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.gigaworks.tech.geticons.BuildConfig
import com.gigaworks.tech.geticons.R
import com.gigaworks.tech.geticons.databinding.IconItemBinding
import com.gigaworks.tech.geticons.domain.Icon
import com.gigaworks.tech.geticons.util.visible

class IconAdapter(private val clickListener: OnIconSetClick) :
    PagingDataAdapter<Icon, IconAdapter.IconViewHolder>(DIFF) {


    inner class IconViewHolder(private val binding: IconItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(icon: Icon) {
            with(binding) {
                author.text = icon.authorName
                type.text = icon.type
                price.text = icon.price
                name.text = icon.name
                premium.visible(icon.isPremium)

                if (icon.imgUrl.isNotEmpty()) {
                    val glideUrl = GlideUrl(
                        icon.imgUrl,
                        LazyHeaders.Builder()
                            .addHeader(
                                "Authorization",
                                "Bearer $AUTH_TOKEN"
                            )
                            .build()
                    )
                    Glide.with(binding.root)
                        .load(glideUrl)
                        .placeholder(R.mipmap.ic_launcher_round)
                        .into(binding.icon)
                }

                root.setOnClickListener {
                    clickListener.onIconSetClick(icon)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = IconItemBinding.inflate(layoutInflater, parent, false)
        return IconViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    interface OnIconSetClick {
        fun onIconSetClick(icon: Icon)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Icon>() {
            override fun areItemsTheSame(oldItem: Icon, newItem: Icon) =
                oldItem.iconId == newItem.iconId

            override fun areContentsTheSame(oldItem: Icon, newItem: Icon) =
                oldItem == newItem
        }
        private const val AUTH_TOKEN = BuildConfig.ACCESS_TOKEN
    }

}