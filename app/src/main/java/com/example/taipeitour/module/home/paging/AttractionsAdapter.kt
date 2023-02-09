package com.example.taipeitour.module.home.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.navOptions
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.taipeitour.Attractions
import com.example.taipeitour.R
import com.example.taipeitour.dataModel.Infos
import com.example.taipeitour.databinding.AttractionsItemBinding
import com.example.taipeitour.utils.ActivityManage
import com.example.taipeitour.utils.SharedInfo


class AttractionsAdapter(val findNavController: NavController) :
    PagingDataAdapter<Attractions, AttractionsAdapter.AttractionsHolder>(COMPARATOR) {

    val attractionsInfos = SharedInfo.instance.getEntity(Infos::class.java)

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
            holder.bind(this, holder.itemView)
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

    inner class AttractionsHolder(private val binding: AttractionsItemBinding) :
        ViewHolder(binding.root) {
        fun bind(attractions: Attractions, itemView: View) {
            var nowPicture: String? = null

            attractionsInfos?.Info?.onEach {
                if (it.Name == attractions.name) {
                    nowPicture = it.Picture1
                    val options = RequestOptions()
                        .priority(Priority.NORMAL)
                        .placeholder(R.drawable.logo)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.logo)

                    Glide.with(ActivityManage.peek()!!)
                        .load(it.Picture1)
                        .apply(options)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.picture)
                }
            }

            binding.attractions = attractions
            binding.executePendingBindings()

            itemView.setOnClickListener {
                val bundle = bundleOf("picture" to nowPicture, "title" to attractions.name, "introduction" to attractions.introduction, "url" to attractions.url)
                findNavController.navigate(R.id.detailFragment, bundle, navOptions {
                    anim {
                        enter = R.anim.slide_in_right
                        exit = R.anim.slide_out_left
                        popEnter = R.anim.slide_in_left
                        popExit = R.anim.slide_out_right
                    }
                })
            }
        }
    }
}