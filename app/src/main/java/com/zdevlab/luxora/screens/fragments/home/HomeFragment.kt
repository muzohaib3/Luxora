package com.zdevlab.luxora.screens.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.FragmentHomeBinding
import com.zdevlab.luxora.logMessage
import com.zdevlab.luxora.screens.adapter.BrowseExcellenceHomeAdapter
import com.zdevlab.luxora.screens.adapter.NewArrivalHomeAdapter
import com.zdevlab.luxora.screens.fragments.home.DataConstants.getBrowseExcellenceList
import com.zdevlab.luxora.screens.fragments.home.DataConstants.getNewArrivalsList
import com.zdevlab.luxora.showMessage

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val firestore = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews(){

        binding.rvBrowseExcellence.apply {
            adapter = BrowseExcellenceHomeAdapter(getBrowseExcellenceList())
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvBrowseExcellence)

        firestore.collection("products").get().addOnSuccessListener {
            if (!it.isEmpty){
                val dataList = it.toObjects(Products::class.java)
                val watchList = dataList.filter { it.product_id == "1" }
                val shoesList = dataList.filter { it.product_id == "2" }

                populateNewArrivalWatches(watchList)
                populateNewArrivalShoes(shoesList)
            }
            else{
                showMessage(requireContext(),"No list found")
            }
        }

        binding.llEdition.setOnClickListener {
            findNavController().navigate(R.id.search)
        }

    }

    private fun populateNewArrivalWatches(list:List<Products>)
    {
        binding.rvNewArrivalsWatches.apply {
            adapter = NewArrivalHomeAdapter(this.context,list)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun populateNewArrivalShoes(list:List<Products>)
    {
        binding.rvNewArrivalsShoes.apply {
            adapter = NewArrivalHomeAdapter(this.context,list)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}