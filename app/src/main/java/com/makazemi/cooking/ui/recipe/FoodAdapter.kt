package com.makazemi.cooking.ui.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.makazemi.cooking.R
import com.makazemi.cooking.databinding.FoodItemRcyBinding
import com.makazemi.cooking.model.Food

class FoodAdapter(private val requestManager: RequestManager) : ListAdapter<Food, FoodAdapter.ViewHolder>(DiffCallback()) {

    private lateinit var clickListener: (item: Food) -> Unit

    fun setClickListener(clickListener: (item: Food) -> Unit) {
        this.clickListener = clickListener
    }


    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding= FoodItemRcyBinding.bind(itemView)
        fun bind(item: Food) {

            binding.txtTitle.text= item.name

            binding.txtCategory.text= item.category_name

            requestManager.load(item.image)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(binding.imgItem)

            itemView.setOnClickListener {
                clickListener(item)
            }


        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.food_item_rcy, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun submitNewList(list:List<Food>){
        submitList(list)
        notifyDataSetChanged()
    }
}