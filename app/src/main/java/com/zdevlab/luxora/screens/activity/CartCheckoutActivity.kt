package com.zdevlab.luxora.screens.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayout
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.ActivityCartCheckoutShippingBinding
import com.zdevlab.luxora.showMessage

class CartCheckoutActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCartCheckoutShippingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCartCheckoutShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews(){

        with(binding){
            btContinueToDeliver.setOnClickListener(this@CartCheckoutActivity)

            tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(tab?.position){
                        0->{

                        }
                        1->{

                        }
                        2->{

                        }
                    }
                }

                override fun onTabUnselected(p0: TabLayout.Tab?) {

                }

                override fun onTabReselected(p0: TabLayout.Tab?) {

                }

            })
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btContinueToDeliver->{

                val fullName = binding.etFullName.text.toString().trim()
                val streetAddress = binding.etFullName.text.toString().trim()
                val city = binding.etFullName.text.toString().trim()
                val postalAddress = binding.etFullName.text.toString().trim()

                when{
                    fullName.isEmpty()->{
                        showMessage(this, "full name missing")
                    }
                    streetAddress.isEmpty()->{
                        showMessage(this, "street address missing")
                    }
                    city.isEmpty()->{
                        showMessage(this, "city missing")
                    }
                    postalAddress.isEmpty()->{
                        showMessage(this, "postal code missing")
                    }
                    else->{
                        submitResponse(fullName, streetAddress, city, postalAddress)
                    }
                }

            }
        }
    }

    private fun submitResponse(fullName:String, stAdd:String, city:String, pAddress: String){

        if (fullName.isNotEmpty() and stAdd.isNotEmpty() and city.isNotEmpty() and pAddress.isNotEmpty()){

        }
        else{
            showMessage(this, "something missing")
        }

    }

}