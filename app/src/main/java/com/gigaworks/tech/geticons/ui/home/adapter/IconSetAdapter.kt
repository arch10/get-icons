package com.gigaworks.tech.geticons.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gigaworks.tech.geticons.databinding.IconSetItemBinding
import com.gigaworks.tech.geticons.domain.IconSet
import com.gigaworks.tech.geticons.util.visible

class IconSetAdapter(private val clickListener: OnIconSetClick) : PagingDataAdapter<IconSet, IconSetAdapter.IconSetViewHolder>(DIFF) {


    inner class IconSetViewHolder(private val binding: IconSetItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(iconSet: IconSet) {
            with(binding) {
                author.text = iconSet.authorName
                type.text = iconSet.type
                price.text = iconSet.price
                name.text = iconSet.name
                license.text = iconSet.license
                premium.visible(iconSet.isPremium)

                root.setOnClickListener {
                    clickListener.onIconSetClick(iconSet)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconSetViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = IconSetItemBinding.inflate(layoutInflater, parent, false)
        return IconSetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IconSetViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    interface OnIconSetClick {
        fun onIconSetClick(iconSet: IconSet)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<IconSet>() {
            override fun areItemsTheSame(oldItem: IconSet, newItem: IconSet) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: IconSet, newItem: IconSet) =
                oldItem == newItem
        }
    }

}