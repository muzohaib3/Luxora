package com.zdevlab.luxora.screens.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.FragmentFilterBottomSheetBinding
import com.zdevlab.luxora.databinding.OrderListBottomSheetBinding
import com.zdevlab.luxora.logMessage
import com.zdevlab.luxora.screens.fragments.tracking.TrackingOrdersBottomSheetAdapter
import com.zdevlab.luxora.screens.models.OrderDetailsModel
import com.zdevlab.luxora.screens.viewmodel.LuxoraViewModel
import java.io.Serializable

class OrdersListBottomSheet(val list:List<OrderDetailsModel>) : BottomSheetDialogFragment() {

    private lateinit var binding: OrderListBottomSheetBinding
    private lateinit var viewmodel: LuxoraViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrderListBottomSheetBinding.inflate(inflater, container, false)
        initViewModel()
        initViews()

        return binding.root
    }

    private fun initViews(){

        if (list != null){
            populateTrackingOrdersList(list)
        }

    }

    private fun initViewModel(){
        viewmodel = ViewModelProvider(this)[LuxoraViewModel::class.java]
    }

    private fun populateTrackingOrdersList(list:List<OrderDetailsModel>){
        binding.rvOrderList.apply {
            adapter = TrackingOrdersBottomSheetAdapter(requireContext(), list, viewmodel)
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

}