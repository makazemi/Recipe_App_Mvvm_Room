package com.makazemi.cooking.ui.listCategory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.makazemi.cooking.R
import com.makazemi.cooking.databinding.CategoryItemRcyBinding
import com.makazemi.cooking.model.CategoryFood
import timber.log.Timber

class CategoryAdapter(private val requestManager: RequestManager) : ListAdapter<CategoryFood, CategoryAdapter.ViewHolder>(DiffCallback()) {

    private lateinit var clickListener: (item: CategoryFood) -> Unit

    fun setClickListener(clickListener: (item: CategoryFood) -> Unit) {
        this.clickListener = clickListener
    }


    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding= CategoryItemRcyBinding.bind(itemView)
        fun bind(item: CategoryFood) {

            binding.txtTitle.text= item.title

            requestManager.load(item.image)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(binding.imgItem)

            itemView.setOnClickListener {
                clickListener(item)
            }


        }

    }

    class DiffCallback : DiffUtil.ItemCallback<CategoryFood>() {
        override fun areItemsTheSame(oldItem: CategoryFood, newItem: CategoryFood): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryFood, newItem: CategoryFood): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.category_item_rcy, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}