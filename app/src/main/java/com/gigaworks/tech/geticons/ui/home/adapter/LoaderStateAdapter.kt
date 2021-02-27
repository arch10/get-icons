package com.gigaworks.tech.geticons.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gigaworks.tech.geticons.databinding.LoadStateFooterBinding
import com.gigaworks.tech.geticons.util.visible

class LoaderStateAdapter: LoadStateAdapter<LoaderStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(private val binding: LoadStateFooterBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.apply {
                loader.visible(loadState is LoadState.Loading)
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LoadStateFooterBinding.inflate(layoutInflater, parent, false)
        return LoadStateViewHolder(binding)
    }

}