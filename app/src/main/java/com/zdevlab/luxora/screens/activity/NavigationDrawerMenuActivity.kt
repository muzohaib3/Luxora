package com.zdevlab.luxora.screens.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.ActivityNavigator
import androidx.recyclerview.widget.LinearLayoutManager
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.ActivityNavigationDrawerMenuBinding
import com.zdevlab.luxora.screens.adapter.NotificationAdapter

class NavigationDrawerMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationDrawerMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationDrawerMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews(){

        val intent = intent.extras
        if (intent != null)
        {
            if (intent.containsKey("destination"))
            {
                when(intent.getString("destination"))
                {
                    "notifications" -> setNotificationsView()
                    "settings" -> setSettingsView()
                    "about" -> setAboutViews()
                }
            }

        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setSettingsView(){
        binding.llAbout.visibility = View.GONE
    }

    private fun setNotificationsView(){
        binding.llAbout.visibility = View.GONE
        binding.llNotifications.visibility = View.VISIBLE

        binding.rvNotification.apply {
            adapter = NotificationAdapter()
            layoutManager = LinearLayoutManager(this@NavigationDrawerMenuActivity)
        }
    }

    private fun setAboutViews(){
        binding.llAbout.visibility = View.VISIBLE
        binding.llNotifications.visibility = View.GONE
    }

}