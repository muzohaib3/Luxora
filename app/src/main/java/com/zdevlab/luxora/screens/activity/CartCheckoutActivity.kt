package com.zdevlab.luxora.screens.activity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.ActivityCartCheckoutShippingBinding
import com.zdevlab.luxora.gotoActivity
import com.zdevlab.luxora.logMessage
import com.zdevlab.luxora.screens.models.CartItemModel
import com.zdevlab.luxora.showMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable

class CartCheckoutActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCartCheckoutShippingBinding
    private var deliveryType = ""
    private var paymentType = ""
    var isComingFromCart = ""
    var cartItemList :ArrayList<CartItemModel>? = null

    val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartCheckoutShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        getIntentDetails()

    }

    private fun getIntentDetails(){

        if (intent.extras != null && intent != null){

            isComingFromCart = intent.extras?.getString("isComingFromCart") ?: ""
            cartItemList = intent.getSerializableExtra("cartList") as? ArrayList<CartItemModel>

            logMessage("cart item list found = $cartItemList")

        }

    }

    private fun initViews(){

        with(binding){

            btContinueToDeliver.setOnClickListener(this@CartCheckoutActivity)
            llCreditCard.setOnClickListener(this@CartCheckoutActivity)
            llCOD.setOnClickListener(this@CartCheckoutActivity)

            cbExpressDelivery.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                    selectDeliveryType(true, false)
                }
            }

            cbStandardDelivery.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                    selectDeliveryType(false, true)
                }
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
                val cardNum = binding.etCardNum.text.toString().trim()
                val expiryDate = binding.etExpiryDate.text.toString().trim()
                val cvv = binding.etCVV.text.toString().trim()


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
                        if (paymentType == "credit card"){
                           if (cardNum.isNotEmpty() && expiryDate.isNotEmpty() && cvv.isNotEmpty()){
                               submitResponse(fullName, streetAddress, city, postalAddress, deliveryType, paymentType)
                           }else{
                               showMessage(this, "information missing")
                           }
                        }else{
                            submitResponse(fullName, streetAddress, city, postalAddress, deliveryType, paymentType)
                        }

                    }
                }

            }

            R.id.llCreditCard->{
                binding.llCreditCardEntry.visibility = View.VISIBLE
                binding.etCardNum.visibility = View.VISIBLE
                selectPaymentMethod(true, false)
            }

            R.id.llCOD->{
                binding.llCreditCardEntry.visibility = View.GONE
                binding.etCardNum.visibility = View.GONE
                selectPaymentMethod(false, true)
            }
        }
    }

    private fun submitResponse(fullName:String, stAdd:String, city:String, pAddress: String, deliveryType: String, paymentType: String){

        if (fullName.isNotEmpty() and stAdd.isNotEmpty() and city.isNotEmpty() and pAddress.isNotEmpty() && deliveryType.isNotEmpty() && paymentType.isNotEmpty()){
            val cartModel = CartCheckoutModel(
                fullName = fullName, streetAdd = stAdd, city = city, pAddress = pAddress,
                deliveryType = deliveryType, paymentType = paymentType, itemsList = cartItemList
            )
            firestore.collection("orders").add(cartModel).addOnSuccessListener { result->
                val refId = result.id
                showCustomDialog(this, cartModel, refId)
                }
                .addOnFailureListener {
                    showDialog(this, "something went wrong")
                }
        }
        else{
            showMessage(this, "something missing")
        }

    }

    private fun gotoActivity(model: CartCheckoutModel, refId: String){

        if (model != null){
            val intent = Intent(this, OrderSuccessActivity::class.java)
            intent.putExtra("cart_model",model)
            intent.putExtra("ref_id",refId)
            startActivity(intent)
        }

    }

    private fun selectDeliveryType(isExpress: Boolean?, isStandard: Boolean?){
        when{
            isStandard == true->{
                deliveryType = "standard"
                binding.cbStandardDelivery.isChecked = true
                binding.cbExpressDelivery.isChecked = false
                binding.llExpressDelivery.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
                binding.llStandardDelivery.setBackgroundResource(R.drawable.light_grey_stroke_yellow_rounded_bg)
            }

            isExpress == true ->{
                deliveryType = "express"
                binding.cbExpressDelivery.isChecked = true
                binding.cbStandardDelivery.isChecked = false
                binding.llExpressDelivery.setBackgroundResource(R.drawable. light_grey_stroke_yellow_rounded_bg)
                binding.llStandardDelivery.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
            }
        }

    }

    private fun selectPaymentMethod(isCreditCard:Boolean?, isCOD: Boolean?){
        when{
            isCreditCard == true->{
                paymentType = "credit card"
                binding.tvCC.setTextColor(ContextCompat.getColor(this, R.color.yellow))
                binding.tvCOD.setTextColor(ContextCompat.getColor(this, R.color.on_primary_fixed_variant))
                binding.ivCC.setColorFilter(ContextCompat.getColor(this, R.color.yellow), PorterDuff.Mode.SRC_IN)
                binding.ivCOD.setColorFilter(ContextCompat.getColor(this, R.color.on_primary_fixed_variant), PorterDuff.Mode.SRC_IN)
                binding.llCreditCard.setBackgroundResource(R.drawable.light_grey_stroke_yellow_rounded_bg)
                binding.llCOD.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
            }
            isCOD == true->{
                paymentType = "cod"
                binding.tvCOD.setTextColor(ContextCompat.getColor(this, R.color.yellow))
                binding.tvCC.setTextColor(ContextCompat.getColor(this, R.color.on_primary_fixed_variant))
                binding.ivCOD.setColorFilter(ContextCompat.getColor(this, R.color.yellow), PorterDuff.Mode.SRC_IN)
                binding.ivCC.setColorFilter(ContextCompat.getColor(this, R.color.on_primary_fixed_variant), PorterDuff.Mode.SRC_IN)
                binding.llCOD.setBackgroundResource(R.drawable.light_grey_stroke_yellow_rounded_bg)
                binding.llCreditCard.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
            }
        }
    }

    fun showDialog(activity: Activity, msg: String?) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_error_msg)

        val text = dialog.findViewById<View?>(R.id.text_dialog) as TextView
        text.setText(msg)

        val dialogButton = dialog.findViewById<View?>(R.id.btn_dialog) as Button
        dialogButton.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(v: View?) {
                dialog.dismiss()
            }
        })

        dialog.show()
    }

    private fun showCustomDialog(context: Context, model:CartCheckoutModel, refId: String){
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_loader_layout, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(true)
        val alertDialog = builder.create()
        alertDialog.show()

        lifecycleScope.launch {
            delay(3000)
            if (alertDialog.isShowing) {
                gotoActivity(model, refId)
                alertDialog.dismiss()
            }
        }

    }

}


data class CartCheckoutModel(
    var fullName:String,
    var streetAdd:String,
    var city:String,
    var pAddress:String,
    var paymentType:String,
    var deliveryType:String,
    var itemsList: ArrayList<CartItemModel>? = null
): Serializable