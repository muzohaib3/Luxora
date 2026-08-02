package com.zdevlab.luxora

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.zdevlab.luxora.LuxoraUtils.LUX_TAG
import java.util.Base64

fun gotoActivity(context: Context, activity: Class<*>){
    val intent = Intent(context, activity)
    context.startActivity(intent)
}

fun gotoActivity(context: Context, key:String, value: String, activity: Class<*>){
    val intent = Intent(context, activity)
    intent.putExtra(key, value)
    context.startActivity(intent)
}

fun showMessage(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun loadImage(context: Context, imgUrl: String, imageView: ImageView){
    Glide.with(context)
        .load(imgUrl)
        .into(imageView);
}

fun logMessage(message:String){
    Log.i(LUX_TAG, message)
}

fun logErrorMessage(message:String){
    Log.e(LUX_TAG, "exception == $message")
}