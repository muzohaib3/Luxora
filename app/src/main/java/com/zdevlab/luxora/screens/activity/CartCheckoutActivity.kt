package com.zdevlab.luxora.screens.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayout
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.ActivityCartCheckoutShippingBinding
import com.zdevlab.luxora.gotoActivity
import com.zdevlab.luxora.showMessage
import java.io.Serializable

class CartCheckoutActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCartCheckoutShippingBinding
    private var deliveryType = ""
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

            llExpressDelivery.setOnClickListener {
                cbExpressDelivery.isChecked = true
            }

            llStandardDelivery.setOnClickListener {
                cbStandardDelivery.isChecked = true
            }

        }

    }

    override fun onClick(view: View?) {
        when(view?.id){

            R.id.btContinueToDeliver->{

                val fullName = binding.etFullName.text.toString().trim()
                val streetAddress = binding.etStreetAddress.text.toString().trim()
                val city = binding.etCity.text.toString().trim()
                val postalAddress = binding.etPostalAddress.text.toString().trim()

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
            val cartModel = CartCheckoutModel(fullName, stAdd, city, pAddress)
            gotoActivity(cartModel)
        }
        else{
            showMessage(this, "something missing")
        }

    }

    private fun gotoActivity(model: CartCheckoutModel){
        val intent = Intent(this, OrderSuccessActivity::class.java)
        intent.putExtra("cart_model",model)
        startActivity(intent)
    }

}



data class CartCheckoutModel(
    var fullName:String,
    var streetAdd:String,
    var city:String,
    var pAddress:String,
): Serializable