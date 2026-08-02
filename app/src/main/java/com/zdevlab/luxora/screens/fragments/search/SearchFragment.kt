package com.zdevlab.luxora.screens.fragments.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.zdevlab.luxora.LuxoraUtils.LUX_TAG
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.FragmentFilterBottomSheetBinding
import com.zdevlab.luxora.databinding.FragmentHomeBinding
import com.zdevlab.luxora.databinding.FragmentSearchBinding
import com.zdevlab.luxora.gotoActivity
import com.zdevlab.luxora.logErrorMessage
import com.zdevlab.luxora.logMessage
import com.zdevlab.luxora.screens.activity.ProductDetailsActivity
import com.zdevlab.luxora.screens.adapter.SearchProductsAdapter
import com.zdevlab.luxora.screens.bottomsheet.FilterBottomSheetFragment
import com.zdevlab.luxora.screens.fragments.home.Products
import com.zdevlab.luxora.screens.listeners.OnItemClickListener
import com.zdevlab.luxora.screens.viewmodel.LuxoraViewModel

class SearchFragment : Fragment(){

    lateinit var binding: FragmentSearchBinding
    private val firebase = Firebase.firestore
    private lateinit var productsList: ArrayList<Products>
    private lateinit var viewModel: LuxoraViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews(){
        initViewModel()

        if (viewModel.getProductsList().isNotEmpty()){
            populateList(viewModel.getProductsList())
        }else{
            getProducts()
        }

        binding.llAdjustFilters.setOnClickListener { setFilter() }
    }

    private fun getProducts(){

        productsList = ArrayList<Products>()
        binding.loader.visibility = View.VISIBLE
        firebase.collection("products").get().addOnSuccessListener { data ->

            if(!data.isEmpty)
            {
                binding.loader.visibility = View.GONE
                for(i in data)
                {
                    val product = i.toObject(Products::class.java)
                    productsList.add(product)
                    viewModel.setProductsList(productsList)
                    populateList(productsList)
                }
            }

        }.addOnFailureListener {
            binding.loader.visibility = View.GONE
            println("the error == ${it.message}")
        }

    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[LuxoraViewModel::class.java]
    }

    private fun populateList(list: ArrayList<Products>){

        if (list.isEmpty()){
            binding.tvCount.text = "0 Items found in Accessories"
        }else{
            binding.tvCount.text = "${list.size} Items found in Accessories"
        }

        try {
            binding.rvProducts.apply {
                adapter = SearchProductsAdapter(this.context ,list, object : OnItemClickListener{
                    override fun onClick(item: Products) {
                        val intent = Intent(requireContext() , ProductDetailsActivity::class.java)
                        intent.putExtra("productModel",item)
                        requireActivity().startActivity(intent)
                    }

                })
                layoutManager = LinearLayoutManager(this.context)
            }
        }catch (e: Exception){
            logErrorMessage("${e.message}")
        }

    }

    private fun setFilter(){

        val sheet = FilterBottomSheetFragment()
        sheet.onDataSubmit = { data->
            logMessage("setFilter --> productType == ${data.productType} and range ${data.priceRange}")

            val productsList = viewModel.getProductsList()
            val watches = productsList.filter { it.product_id == "1" }
            val shoes = productsList.filter { it.product_id == "2" }

            when(data.productType){
                "watches" -> populateList(watches as ArrayList<Products>)
                "shoes" -> populateList(shoes as ArrayList<Products>)
            }
        }
        sheet.show(childFragmentManager, "")

    }

}