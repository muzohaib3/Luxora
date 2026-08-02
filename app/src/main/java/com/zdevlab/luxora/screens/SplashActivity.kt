package com.zdevlab.luxora.screens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.zdevlab.luxora.LuxoraUtils.LUX_TAG
import com.zdevlab.luxora.R
import com.zdevlab.luxora.gotoActivity
import com.zdevlab.luxora.screens.activity.HomeActivity
import com.zdevlab.luxora.screens.activity.LoginActivity
import com.zdevlab.luxora.utils.LuxoraPreferences

class SplashActivity : AppCompatActivity() {

    private val preferences =  LuxoraPreferences()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        gotoScreens()

    }

    private fun gotoScreens(){
        try {
            val isLogin = preferences.getBoolean(this, "isLogin") ?: false
            when(isLogin){
                true ->{
                    Handler().postDelayed({
                        gotoActivity(this, HomeActivity::class.java)
                    },3000)
                }
                false->{
                    Handler().postDelayed({
                        gotoActivity(this,LoginActivity::class.java)
                    },3000)
                }
                else->{
                    Handler().postDelayed({
                        gotoActivity(this,LoginActivity::class.java)
                    },3000)
                }
            }
        }catch (e: Exception){
            Log.i(LUX_TAG, "exception splash = ${e.message}")
        }

    }

}