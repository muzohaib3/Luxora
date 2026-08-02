package com.zdevlab.luxora.screens.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.zdevlab.luxora.LuxoraUtils.LUX_TAG
import com.zdevlab.luxora.R
import com.zdevlab.luxora.application.LuxoraApplication
import com.zdevlab.luxora.databinding.ActivityProductDetailsBinding
import com.zdevlab.luxora.gotoActivity
import com.zdevlab.luxora.loadImage
import com.zdevlab.luxora.logMessage
import com.zdevlab.luxora.screens.fragments.home.NewArrivalsModel
import com.zdevlab.luxora.screens.fragments.home.Products
import com.zdevlab.luxora.screens.models.CartItemModel
import com.zdevlab.luxora.screens.viewmodel.LuxoraViewModel
import com.zdevlab.luxora.screens.viewmodel.MainRepository

class ProductDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProductDetailsBinding
    private var selectedSize:String? = ""
    private lateinit var viewModel: LuxoraViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initViewModel()

    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[LuxoraViewModel::class.java]
    }

    private fun initViews(){

        with(binding){
            size1.setOnClickListener(this@ProductDetailsActivity)
            size2.setOnClickListener(this@ProductDetailsActivity)
            size3.setOnClickListener(this@ProductDetailsActivity)
            btBuyNow.setOnClickListener(this@ProductDetailsActivity)
        }

        val intent = intent.extras
        if (intent != null){

            when{

                intent.containsKey("productModel") ->{
                    Log.i(LUX_TAG,"1 --> productModel")
                    val model = intent.getSerializable("productModel")
                    Log.i(LUX_TAG,"model = $model")
                    setViews(model as Products)
                }

                intent.containsKey("newArrival") ->{
                    Log.i(LUX_TAG,"2 --> newArrival")
                    val model = intent.getSerializable("newArrival") as Products
                    setViewNewArrival(model)
                }

            }
        }
    }

    private fun selectSize(size1:Boolean, size2:Boolean, size3: Boolean){
        when{
            size1 == true->{
                binding.size1.setBackgroundResource(R.drawable.light_grey_stroke_yellow_rounded_bg)
                binding.size2.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
                binding.size3.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
            }
            size2 == true->{
                binding.size1.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
                binding.size2.setBackgroundResource(R.drawable.light_grey_stroke_yellow_rounded_bg)
                binding.size3.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
            }
            size3 == true->{
                binding.size1.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
                binding.size2.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
                binding.size3.setBackgroundResource(R.drawable.light_grey_stroke_yellow_rounded_bg)
            }
        }
    }

    private fun setViews(model: Products){
        with(binding){
            loadImage(this@ProductDetailsActivity, model.imgUrl ?: "", ivProduct)
            tvReviews.text = model.reviews
            tvRating.text = model.rating
            tvProductPrice.text = model.price
            tvProductDetails.text = model.product_details

            btAddToCart.setOnClickListener {
                addToCart(name = model.name?:"", price = model.price?:"", img = model.imgUrl?:"")
            }

        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.size1->{
                selectedSize = "1"
                selectSize(true,false,false)
            }
            R.id.size2->{
                selectedSize = "2"
                selectSize(false,true,false)
            }
            R.id.size3->{
                selectedSize = "3"
                selectSize(false,false,true)
            }
            R.id.btBuyNow->{
                gotoActivity(this, CartCheckoutActivity::class.java)
            }
        }
    }

    private fun addToCart(img:String, name:String, price:String){

        val cartModel = CartItemModel(name,"$name Is very good",price,img)
        viewModel.addProduct(cartModel)
        finish()

    }

    private fun setViewNewArrival(newArrivalModel: Products){
        with(binding){

            loadImage(this@ProductDetailsActivity, newArrivalModel.imgUrl ?: "", ivProduct)
            tvReviews.text = newArrivalModel.reviews
            tvRating.text = newArrivalModel.rating
            tvProductPrice.text = newArrivalModel.price
            tvProductDetails.text = newArrivalModel.product_details

            btAddToCart.setOnClickListener {
                addToCart(name = newArrivalModel.name?:"", price = newArrivalModel.price?:"", img = newArrivalModel.imgUrl?:"")
            }

        }
    }

}