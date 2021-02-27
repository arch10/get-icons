package com.gigaworks.tech.geticons.ui.authordetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gigaworks.tech.geticons.databinding.IconSetItemBinding
import com.gigaworks.tech.geticons.domain.IconSet
import com.gigaworks.tech.geticons.util.visible


class IconSetAdapter(
    private val iconSetList: List<IconSet>,
    private val clickListener: OnIconSetClickListener
) : RecyclerView.Adapter<IconSetAdapter.IconSetViewHolder>() {
    interface OnIconSetClickListener {
        fun onIconSetClick(icon: IconSet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconSetViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = IconSetItemBinding.inflate(layoutInflater, parent, false)
        return IconSetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IconSetViewHolder, position: Int) {
        holder.bind(iconSetList[position])
    }

    override fun getItemCount() = iconSetList.size

    inner class IconSetViewHolder(private val binding: IconSetItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(icon: IconSet) {
            with(binding) {
                name.text = icon.name
                type.text = icon.type
                price.text = icon.price
                premium.visible(icon.isPremium)
                author.text = icon.authorName
                root.setOnClickListener {
                    clickListener.onIconSetClick(icon)
                }
            }
        }
    }

}