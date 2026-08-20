package com.zdevlab.luxora.screens.fragments.tracking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.TrackingItemLayoutBinding
import com.zdevlab.luxora.loadImage
import com.zdevlab.luxora.logMessage
import com.zdevlab.luxora.screens.bottomsheet.OrdersListBottomSheet
import com.zdevlab.luxora.screens.models.OrdersModel
import com.zdevlab.luxora.screens.viewmodel.LuxoraViewModel

class TrackingOrdersAdapter(
    val context: Context,
    private val list:ArrayList<OrdersModel>,
    val viewModel: LuxoraViewModel
) : RecyclerView.Adapter<TrackingOrdersAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = TrackingItemLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tracking_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]

        with(holder.binding){

            viewModel.setOrdersIndexDetails(position, data.streetAdd ?: "", data.city ?: "")
            tvUsername.text = data.fullName
            tvPaymentType.text = data.paymentType
            tvDeliveryType.text = data.deliveryType
            tvPaymentAddress.text = "${data.streetAdd},${data.city}. ${data.paddress}"
            viewModel.setTrackingOrderList(data.itemsList ?: emptyList())

            cvOrder.setOnClickListener {
                val bottomsheet = OrdersListBottomSheet(data.itemsList ?: emptyList())
                bottomsheet.show((context as AppCompatActivity).supportFragmentManager, "OrdersListBottomSheet")
            }

        }

    }



    override fun getItemCount() = list.size
}