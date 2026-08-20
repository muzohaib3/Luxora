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
import com.zdevlab.luxora.screens.models.BuyNowModel
import com.zdevlab.luxora.screens.models.CartItemModel
import com.zdevlab.luxora.screens.viewmodel.LuxoraViewModel
import com.zdevlab.luxora.screens.viewmodel.MainRepository
import com.zdevlab.luxora.showMessage

class ProductDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProductDetailsBinding
    private var selectedSize:String? = ""
    private lateinit var viewModel: LuxoraViewModel
    private var isComingFromJourney = ""
    private var selectedColor = ""
    private var searchModel: Products? = null

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
            llBrown.setOnClickListener(this@ProductDetailsActivity)
            llBlack.setOnClickListener(this@ProductDetailsActivity)
            llGray.setOnClickListener(this@ProductDetailsActivity)
            llBlue.setOnClickListener(this@ProductDetailsActivity)
        }

        val intent = intent.extras
        if (intent != null){

            when{

                intent.containsKey("productModel") -> {
                    isComingFromJourney = "Search"
                    Log.i(LUX_TAG,"1 --> productModel")
                    searchModel = intent.getSerializable("productModel") as Products
                    Log.i(LUX_TAG,"model = $searchModel")
                    setViews(searchModel ?: Products())
                }

                intent.containsKey("newArrival") -> {
                    isComingFromJourney = "HomeNewArrival"
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
                if (selectedSize?.isNotEmpty() == true){
                    addToCart(name = model.name?:"", price = model.price?:"", img = model.imgUrl?:"")
                }else{
                    showMessage(this@ProductDetailsActivity, "please select any size")
                }
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

            R.id.llBlue->{ selectColor(false, false, false, true) }

            R.id.llBlack->{ selectColor(false, true, false, false) }

            R.id.llBrown->{ selectColor(true, false, false, false) }

            R.id.llGray->{ selectColor(false, false, true, false) }

            R.id.btBuyNow->{
                if (selectedSize?.isNotEmpty() == true){
                    when(isComingFromJourney){
                        "HomeNewArrival"->{
//                        val buyModel = BuyNowModel()
//                        viewModel.setBuyNowModel()
                        }
                        "Search"->{
                            val buyModel = BuyNowModel(
                                searchModel?.name ?: "",
                                searchModel?.product_details ?: "",
                                searchModel?.price ?: "",
                                searchModel?.imgUrl ?: "",
                            )
                            if (viewModel != null) {
                                MainRepository.setBuyNowModel(buyModel)
                                gotoActivity(this, CartCheckoutActivity::class.java)
                            }
                        }
                    }
                }else{
                    showMessage(this,"please select any size")
                }
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
                if (selectedSize?.isNotEmpty() == true){
                    addToCart(name = newArrivalModel.name?:"", price = newArrivalModel.price?:"", img = newArrivalModel.imgUrl?:"")
                }else{
                    showMessage(this@ProductDetailsActivity, "please select any size")
                }

            }

        }
    }

    private fun selectColor(brown:Boolean, black:Boolean, gray:Boolean, blue: Boolean){
        with(binding){
            when
            {
                brown == true->{
                    llBrown.setBackgroundResource(R.drawable.brown_solid_yellow_stroke_bg)
                    llGray.setBackgroundResource(R.drawable.grey_layout_stroke_selector)
                    llBlue.setBackgroundResource(R.drawable.blue_layout_stroke_selector)
                    llBlack.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
                }
                black == true->{
                    llBrown.setBackgroundResource(R.drawable.brown_layout_stroke_selector)
                    llGray.setBackgroundResource(R.drawable.grey_layout_stroke_selector)
                    llBlue.setBackgroundResource(R.drawable.blue_layout_stroke_selector)
                    llBlack.setBackgroundResource(R.drawable.light_grey_stroke_yellow_rounded_bg)
                }
                gray == true->{
                    llBrown.setBackgroundResource(R.drawable.brown_layout_stroke_selector)
                    llGray.setBackgroundResource(R.drawable.light_gray_color_yellow_stroke_rounded_bg)
                    llBlue.setBackgroundResource(R.drawable.blue_layout_stroke_selector)
                    llBlack.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
                }
                blue == true->{
                    llBrown.setBackgroundResource(R.drawable.brown_layout_stroke_selector)
                    llGray.setBackgroundResource(R.drawable.grey_layout_stroke_selector)
                    llBlue.setBackgroundResource(R.drawable.blue_color_yellow_stroke_rounded_bg)
                    llBlack.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
                }

            }
        }
    }

}