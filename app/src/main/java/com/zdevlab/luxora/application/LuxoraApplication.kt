package com.zdevlab.luxora.application

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.FirebaseApp
import com.zdevlab.luxora.screens.viewmodel.LuxoraViewModel

class LuxoraApplication: Application() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate() {
        super.onCreate()

        if (getProcessName() != applicationInfo.processName) {
            FirebaseApp.initializeApp(applicationContext)
        }

    }
}