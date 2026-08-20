package com.zdevlab.luxora.screens.fragments.tracking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.OrderListDetailItemBottomSheetBinding
import com.zdevlab.luxora.databinding.TrackingItemLayoutBinding
import com.zdevlab.luxora.loadImage
import com.zdevlab.luxora.logMessage
import com.zdevlab.luxora.screens.models.OrderDetailsModel
import com.zdevlab.luxora.screens.models.OrdersModel
import com.zdevlab.luxora.screens.viewmodel.LuxoraViewModel

class TrackingOrdersBottomSheetAdapter(
    val context: Context,
    private val list: List<OrderDetailsModel>,
    val viewModel: LuxoraViewModel
) : RecyclerView.Adapter<TrackingOrdersBottomSheetAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = OrderListDetailItemBottomSheetBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_list_detail_item_bottom_sheet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]

        with(holder.binding){

            tvProductName.text = data.productName
            tvProductDetails.text = data.productDetail
            tvAmount.text = data.productAmount
            loadImage(context, data.productImg ?: "", ivProduct)

        }

    }



    override fun getItemCount() = list.size
}