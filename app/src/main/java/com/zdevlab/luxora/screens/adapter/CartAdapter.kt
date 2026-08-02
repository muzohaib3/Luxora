package com.zdevlab.luxora.screens.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zdevlab.luxora.LuxoraUtils.LUX_TAG
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.BrowseExcellenceHomeLayoutBinding
import com.zdevlab.luxora.databinding.CartItemLayoutBinding
import com.zdevlab.luxora.databinding.ProductsItemLayoutBinding
import com.zdevlab.luxora.loadImage
import com.zdevlab.luxora.screens.fragments.home.Products
import com.zdevlab.luxora.screens.fragments.home.ProductsDetailsModel
import com.zdevlab.luxora.screens.listeners.OnItemClickListener
import com.zdevlab.luxora.screens.models.CartItemModel
import com.zdevlab.luxora.screens.viewmodel.LuxoraViewModel
import kotlin.collections.get

class CartAdapter(
    val context: Context,
    private val list:List<CartItemModel>,
    private val viewmodel: LuxoraViewModel
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    var quantity = 0
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CartItemLayoutBinding.bind(itemView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding)
        {
            loadImage(context, item.productImg, ivProduct)
            tvPrice.text = item.productAmount
            tvProduct.text = item.productName
            tvDetail.text = item.productDetail

            ivAdd.setOnClickListener {
                quantity++
                viewmodel.setQuantity(if(quantity < 0 ) 0 else quantity)
                tvQuantity.text = if(quantity < 0 ) "0" else quantity.toString()
            }
            ivMinus.setOnClickListener {
                quantity --
                viewmodel.setQuantity(if(quantity < 0 ) 0 else quantity)
                tvQuantity.text = if(quantity < 0 ) "0" else quantity.toString()
            }

            Log.i(LUX_TAG, "increment value == $quantity")
        }

    }

    override fun getItemCount() = list.size
}