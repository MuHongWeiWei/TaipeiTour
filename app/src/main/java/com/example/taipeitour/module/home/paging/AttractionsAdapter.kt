package com.example.taipeitour.module.home.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taipeitour.Attractions
import com.example.taipeitour.databinding.AttractionsItemBinding

class AttractionsAdapter :
    PagingDataAdapter<Attractions, AttractionsAdapter.AttractionsHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Attractions>() {
            override fun areItemsTheSame(oldItem: Attractions, newItem: Attractions): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Attractions, newItem: Attractions): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: AttractionsHolder, position: Int) {
        getItem(position)?.apply {
            holder.bind(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AttractionsHolder(
            AttractionsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    class AttractionsHolder(private val binding: AttractionsItemBinding) :
        ViewHolder(binding.root) {
        fun bind(attractions: Attractions) {
            binding.attractions = attractions
            binding.executePendingBindings()
        }
    }
}