package com.zdevlab.luxora.screens.models

import java.io.Serial
import java.io.Serializable

data class OrdersModel(
    val city:String? = "",
    val deliveryType:String? = "",
    val fullName:String? = "",
    val paddress:String? = "",
    val paymentType:String? = "",
    val streetAdd:String? = "",
    val itemsList:List<OrderDetailsModel>? = emptyList(),
): Serializable

data class OrderDetailsModel(
    val productAmount: String? = "",
    val productDetail: String? = "",
    val productImg: String? = "",
    val productName: String? = "",
): Serializable
