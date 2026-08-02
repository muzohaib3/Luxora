package com.zdevlab.luxora.screens.fragments.home

import com.zdevlab.luxora.R
import java.io.Serializable

object DataConstants {

    fun getBrowseExcellenceList():List<ProductsDetailsModel>{
        return listOf(
            ProductsDetailsModel("Watch",R.drawable.watch1),
            ProductsDetailsModel("Watch",R.drawable.shoes_img),
            ProductsDetailsModel("Watch",R.drawable.watch1),
        )
    }

    fun getNewArrivalsList():List<NewArrivalsModel>{
        return listOf(
            NewArrivalsModel("Watch",R.drawable.watch1,100),
            NewArrivalsModel("Watch",R.drawable.shoes_img,20),
            NewArrivalsModel("Watch",R.drawable.watch1,50),
        )
    }

}

data class ProductsDetailsModel(
    val name: String,
    val image: Int
)

data class NewArrivalsModel(
    val name: String,
    val image: Int,
    val price: Int,
): Serializable


data class Products(
    val name:String? = "",
    val price:String? = "",
    val inStock:Boolean? = false,
    val imgUrl:String? = "",
    val product_id: String? = "",
    val product_details: String? = "",
    val reviews: String? = "",
    val rating: String? = ""
): Serializable