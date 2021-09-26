package com.makazemi.cooking.ui.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.makazemi.cooking.R
import com.makazemi.cooking.databinding.RawMaterialItemRcyBinding
import com.makazemi.cooking.model.RawMaterial

class RawMaterialAdapter() : ListAdapter<RawMaterial, RawMaterialAdapter.ViewHolder>(DiffCallback()) {


    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding= RawMaterialItemRcyBinding.bind(itemView)
        fun bind(item: RawMaterial) {

            binding.txtNameRawMaterial.text= item.nameMaterial

            binding.txtValueRawMaterial.text= item.value

        }

    }

    class DiffCallback : DiffUtil.ItemCallback<RawMaterial>() {
        override fun areItemsTheSame(oldItem: RawMaterial, newItem: RawMaterial): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RawMaterial, newItem: RawMaterial): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.raw_material_item_rcy, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}