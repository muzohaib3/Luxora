package com.zdevlab.luxora.screens.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.BrowseExcellenceHomeLayoutBinding
import com.zdevlab.luxora.screens.fragments.home.ProductsDetailsModel
import kotlin.collections.get

class BrowseExcellenceHomeAdapter(
    private val list:List<ProductsDetailsModel>
) : RecyclerView.Adapter<BrowseExcellenceHomeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = BrowseExcellenceHomeLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.browse_excellence_home_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding){
            ivProduct.setImageResource(item.image)
            tvProduct.text = item.name
        }

    }

    override fun getItemCount() = list.size
}