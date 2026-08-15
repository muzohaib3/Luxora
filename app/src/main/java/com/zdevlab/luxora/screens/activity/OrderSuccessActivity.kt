package com.zdevlab.luxora.screens.activity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.ActivityOrderSuccessBinding
import com.zdevlab.luxora.gotoActivity
import com.zdevlab.luxora.screens.viewmodel.LuxoraViewModel
import com.zdevlab.luxora.screens.viewmodel.MainRepository

class OrderSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderSuccessBinding
    private lateinit var viewmodel: LuxoraViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews(){

        val buyNowModel = MainRepository.buyNowModel
        initViewModel()

        val intent = intent.extras
        if (intent != null){
            if (intent.containsKey("ref_id")){
                val refId = intent.getString("ref_id")
                binding.tvOrderId.text = "Order Id: "+refId
            }
            if (intent.containsKey("cart_model")){
                val model = intent.getSerializable("cart_model") as CartCheckoutModel
                binding.apply {
                    productName.text = buyNowModel.value?.productName
                    tvUserName.text = model.fullName
                    tvShippingAddress.text = "Shipping Address\n${model.streetAdd}, ${model.pAddress}"
                    tvAmount.text = buyNowModel.value?.productAmount ?: "0"
                }
            }
        }

        binding.btTrackOrder.setOnClickListener {
            gotoActivity(this, "isTrackingOrder","1", HomeActivity::class.java)
        }

        binding.btBack.setOnClickListener {
            gotoActivity(this, "isCheckedOut","1", HomeActivity::class.java)
        }

    }

    private fun initViewModel(){
        viewmodel = ViewModelProvider(this)[LuxoraViewModel::class.java]
    }

}