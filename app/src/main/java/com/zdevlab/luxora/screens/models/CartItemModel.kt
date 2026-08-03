package com.zdevlab.luxora.screens.models

import java.io.Serializable
import java.time.temporal.TemporalAmount

data class CartItemModel(
    val productName: String,
    val productDetail: String,
    val productAmount: String,
    val productImg: String,
): Serializable

data class BuyNowModel(
    val productName: String,
    val productDetail: String,
    val productAmount: String,
    val productImg: String,
)