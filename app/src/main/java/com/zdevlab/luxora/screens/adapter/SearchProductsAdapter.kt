package com.zdevlab.luxora.screens.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.BrowseExcellenceHomeLayoutBinding
import com.zdevlab.luxora.databinding.ProductsItemLayoutBinding
import com.zdevlab.luxora.loadImage
import com.zdevlab.luxora.screens.fragments.home.Products
import com.zdevlab.luxora.screens.fragments.home.ProductsDetailsModel
import com.zdevlab.luxora.screens.listeners.OnItemClickListener
import kotlin.collections.get

class SearchProductsAdapter(
    val context: Context,
    private val list:List<Products>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<SearchProductsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ProductsItemLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.products_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding){
            tvProductName.text = item.name
            tvPrice.text = item.price
            loadImage(context, item.imgUrl ?: "" , ivProduct)
            llProduct.setOnClickListener {
                listener.onClick(item)
            }
        }

    }

    override fun getItemCount() = list.size
}