package com.zdevlab.luxora.screens.activity

import android.app.FragmentManager
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.zdevlab.luxora.databinding.ActivityHomeBinding
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.zdevlab.luxora.R
import com.zdevlab.luxora.gotoActivity
import com.zdevlab.luxora.screens.fragments.cart.CartFragment
import androidx.core.view.get

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.root)

        initViews()
//        showCheckout()

    }
    private fun initViews() {
        val navController = findNavController(R.id.navController)
        binding.bottomNavigationView.setupWithNavController(navController)

        // Drawer Toggle (Toolbar menu button click)
        binding.ivMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        // Drawer Menu Clicks
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.nav_sync->{

                }
                R.id.nav_notification->{
                    gotoActivity(this, "destination","notifications", NavigationDrawerMenuActivity::class.java)
                }
                R.id.nav_about->{
                    gotoActivity(this, "destination","about", NavigationDrawerMenuActivity::class.java)
                }
                R.id.nav_settings->{
                    gotoActivity(this, "destination","settings", NavigationDrawerMenuActivity::class.java)
                }
                R.id.nav_logout -> {

                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val drawerLayout = binding.drawerLayout
        val mainContent = binding.llMain

        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val scaleFactor = 1 - (slideOffset * 0.1f)
                mainContent.scaleX = scaleFactor
                mainContent.scaleY = scaleFactor
                mainContent.pivotX = 0f
                mainContent.translationX = drawerView.width * slideOffset
            }

            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {
                mainContent.scaleX = 1f
                mainContent.scaleY = 1f
                mainContent.translationX = 0f
            }
            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

//
//    private fun showCheckout(){
//
//        val intent = intent.extras
//        if (intent != null){
//
//            when{
//                intent.containsKey("isCheckout")->{
//                    val isCheckout = intent.getString("isCheckout", "0") ?: ""
//                    println("isCheckout $isCheckout")
//                    findNavController(R.id.navController).navigate(R.id.checkout)
////                    binding.bottomNavigationView.menu.findItem(2).isChecked = true
//                }
//            }
//
//        }
//
//    }



}