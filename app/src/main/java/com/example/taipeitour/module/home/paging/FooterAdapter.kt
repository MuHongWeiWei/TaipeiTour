package com.example.taipeitour.module.home.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taipeitour.databinding.FooterItemBinding

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2023/2/9
 */
class FooterAdapter(private val retry: () -> Unit) : LoadStateAdapter<FooterAdapter.FooterViewHolder>() {

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        return FooterViewHolder(FooterItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        )
    }

    class FooterViewHolder(private val binding: FooterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState, retry: () -> Unit) {
            binding.retryButton.setOnClickListener {
                retry()
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
        }
    }
}
