package com.zdevlab.luxora.screens.models

import java.time.Instant

data class UserModel(
    var email:String? = "",
    var username:String? = "",
    var password:String? = "",
    var cardNum:String? = "",
    var createdAt: String = "",
    var phoneNum:String? = "",
    var primaryAddress:String? = "",
    var profileImg:String? = "",
    var userType:Int? = 0,
)