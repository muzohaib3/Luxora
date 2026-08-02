package com.zdevlab.luxora.screens.dialog

import android.app.Activity
import android.app.AlertDialog
import com.zdevlab.luxora.R

class DialogLoader(private val activity: Activity) {
    private var dialog: AlertDialog? = null

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.alert_dialog_loader, null))
        builder.setCancelable(false) // Prevents the user from canceling by tapping outside

        dialog = builder.create()
        dialog?.show()
    }

    fun dismissDialog() {
        dialog?.dismiss()
    }
}