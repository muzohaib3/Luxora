package com.zdevlab.luxora.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zdevlab.luxora.screens.bottomsheet.FilterModel
import com.zdevlab.luxora.screens.fragments.home.Products
import com.zdevlab.luxora.screens.models.BuyNowModel
import com.zdevlab.luxora.screens.models.CartItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LuxoraViewModel(): ViewModel(){
    var _productsList: ArrayList<Products> = ArrayList()

    fun getProductsList(): ArrayList<Products>{
        return _productsList
    }

    fun setProductsList(list: ArrayList<Products>){
        _productsList = list
    }

    val cartItems = MainRepository.cartItems

    fun addProduct(product: CartItemModel) {
        MainRepository.addToCart(product)
    }

    private var _buyNow =  MutableLiveData<BuyNowModel>()
    val buyNow:LiveData<BuyNowModel> get() = _buyNow

    fun setBuyNowModel(data: BuyNowModel){
        _buyNow.value = data
    }

    fun getBuyNowModel():LiveData<BuyNowModel>{
        return _buyNow
    }


    private val _quantity = MutableLiveData<Int>()
    val savedQuantity: LiveData<Int> get() = _quantity

    fun setQuantity(quantity: Int) {
        _quantity.value = quantity
    }

    var productBasePrice = ""


}