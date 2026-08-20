package com.zdevlab.luxora.screens.fragments.tracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.FragmentTrackingBinding
import com.zdevlab.luxora.getScaledBitmapIcon
import com.zdevlab.luxora.logErrorMessage
import com.zdevlab.luxora.logMessage
import com.zdevlab.luxora.screens.models.OrdersModel
import com.zdevlab.luxora.screens.viewmodel.LuxoraViewModel


class TrackingFragment : Fragment() , OnMapReadyCallback{

    private lateinit var mMap: GoogleMap
    private val firestore = Firebase.firestore
    private lateinit var binding: FragmentTrackingBinding
    private var ordersList = ArrayList<OrdersModel>()
    private lateinit var viewmodel: LuxoraViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentTrackingBinding.inflate(inflater,container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        initViewModel()
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        val sydney = LatLng(-34.0, 151.0)
        val smallIcon = getScaledBitmapIcon(requireActivity(), R.drawable.order, 48, 48)
        mMap.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Delivery Address")
                .snippet("D5 Noman Garden")
                .icon(smallIcon)

        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

//        val areaBounds = Arrays.asList<LatLng?>(
//            LatLng(24.9000, 67.0800),
//            LatLng(24.9100, 67.0800),
//            LatLng(24.9100, 67.0900),
//            LatLng(24.9000, 67.0900)
//        )
//        val polygonOptions = PolygonOptions()
//            .addAll(areaBounds)
//            .strokeColor(Color.BLUE)
//            .strokeWidth(5f)
//            .fillColor(Color.argb(50, 0, 0, 255))
//        mMap.addPolygon(polygonOptions)

        setOrdersList()

        if (viewmodel != null){

            viewmodel.currentStreet.observe(this){ data->
                logMessage("street --> $data")
            }

        }

    }

    private fun setOrdersList(){

        firestore.collection("orders").get().addOnSuccessListener { result->
            if (!result.isEmpty){

                for(i in result){
                    val data = i.toObject<OrdersModel>()
                    ordersList.add(data)
                }

                binding.rvOrders.apply {
                    adapter = TrackingOrdersAdapter(requireActivity(),ordersList, viewmodel)
                    layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                }
                val snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(binding.rvOrders)
            }
        }.addOnFailureListener { exception->
            logErrorMessage("${exception.message}")
        }

    }

    private fun initViewModel(){
        viewmodel = ViewModelProvider(this)[LuxoraViewModel::class.java]
    }

}
