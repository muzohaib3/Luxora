package com.zdevlab.luxora.screens.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.BrowseExcellenceHomeLayoutBinding
import com.zdevlab.luxora.databinding.NewArrivalsHomeLayoutBinding
import com.zdevlab.luxora.loadImage
import com.zdevlab.luxora.screens.activity.ProductDetailsActivity
import com.zdevlab.luxora.screens.fragments.home.NewArrivalsModel
import com.zdevlab.luxora.screens.fragments.home.Products
import com.zdevlab.luxora.screens.fragments.home.ProductsDetailsModel
import kotlin.collections.get

class NewArrivalHomeAdapter(
    val context: Context,
    private val list:List<Products>
) : RecyclerView.Adapter<NewArrivalHomeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = NewArrivalsHomeLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.new_arrivals_home_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        with(holder.binding){
            loadImage(context, item.imgUrl ?: "", ivProduct)
            tvProduct.text = item.name
            tvPrice.text = item.price.toString()

            llMain.setOnClickListener {
                sendItemObj(item,ProductDetailsActivity::class.java)
            }

        }
    }

    override fun getItemCount() = list.size

    fun sendItemObj(data: Products,destination: Class<*>){
        val intent = Intent(context, destination)
        intent.putExtra("newArrival", data)
        context.startActivity(intent)
    }

}