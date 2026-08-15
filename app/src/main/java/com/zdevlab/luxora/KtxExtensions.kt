package com.zdevlab.luxora

import android.R
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import java.io.Serializable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.zdevlab.luxora.LuxoraUtils.LUX_TAG


fun gotoActivity(context: Context, activity: Class<*>){
    val intent = Intent(context, activity)
    context.startActivity(intent)
}

fun gotoActivity(context: Context, key:String, value: String, activity: Class<*>){
    val intent = Intent(context, activity)
    intent.putExtra(key, value)
    context.startActivity(intent)
}

fun <T : Serializable> gotoActivity(context: Context, key1: String, value1: String, key2: String, value2: ArrayList<T>, activity: Class<*>) {
    val intent = Intent(context, activity)
    intent.putExtra(key1, value1)
    intent.putExtra(key2, value2)
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

fun Fragment.gotoFragment(destination: Int){
    findNavController().navigate(destination)
}

fun Fragment.gotoFragment(destination: Int, key:String , data: String){
    val bundle = bundleOf(key to data)
    findNavController().navigate(destination, bundle)
}



