package com.gigaworks.tech.geticons.ui.iconsetdetails.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.gigaworks.tech.geticons.BuildConfig
import com.gigaworks.tech.geticons.databinding.IconItemBinding
import com.gigaworks.tech.geticons.domain.Icon
import com.gigaworks.tech.geticons.util.visible

class IconAdapter(private val clickListener: OnIconClickListener) : PagingDataAdapter<Icon, IconAdapter.IconViewHolder>(DIFF) {

    interface OnIconClickListener {
        fun onIconClick(icon: Icon)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        Log.d("App", "onBindViewHolder: ")
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        Log.d("App", "onBindViewHolder: ")
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = IconItemBinding.inflate(layoutInflater, parent, false)
        return IconViewHolder(binding)
    }

    inner class IconViewHolder(private val binding: IconItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(icon: Icon) {
            Log.d("App", "bind: ${icon.iconId}")
            with(binding) {
                name.text = icon.name
                type.text = icon.type
                price.text = icon.price
                premium.visible(icon.isPremium)

//                val glideUrl = GlideUrl(
//                    icon.imgUrl,
//                    LazyHeaders.Builder()
//                        .addHeader("Authorization", "Bearer $AUTH_TOKEN")
//                        .build()
//                )
//                Glide.with(binding.root)
//                    .load(glideUrl)
//                    .into(binding.icon)

                root.setOnClickListener {
                    clickListener.onIconClick(icon)
                }
            }
        }
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