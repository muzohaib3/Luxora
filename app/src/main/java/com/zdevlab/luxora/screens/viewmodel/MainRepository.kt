package com.zdevlab.luxora.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zdevlab.luxora.screens.models.BuyNowModel
import com.zdevlab.luxora.screens.models.CartItemModel

object MainRepository {

    private val _cartItems = MutableLiveData<MutableList<CartItemModel>>(mutableListOf())
    val cartItems: LiveData<MutableList<CartItemModel>> get() = _cartItems

    fun addToCart(product: CartItemModel) {
        val currentList = _cartItems.value ?: mutableListOf()
        currentList.add(product)
        _cartItems.value = currentList
    }

    private val _buyNowModel = MutableLiveData<BuyNowModel>()
    val buyNowModel: LiveData<BuyNowModel> get() = _buyNowModel

    fun setBuyNowModel(data: BuyNowModel){
        _buyNowModel.value = data
    }

}