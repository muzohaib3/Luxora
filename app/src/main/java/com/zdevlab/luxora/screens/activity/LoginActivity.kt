package com.zdevlab.luxora.screens.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.zdevlab.luxora.LuxoraUtils
import com.zdevlab.luxora.LuxoraUtils.LUX_TAG
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.ActivityLoginBinding
import com.zdevlab.luxora.gotoActivity
import com.zdevlab.luxora.screens.dialog.DialogLoader
import com.zdevlab.luxora.showMessage
import com.zdevlab.luxora.utils.LuxoraPreferences

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityLoginBinding
    private val firebaseDb = Firebase.firestore
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

    }

    private fun initViews() {

        binding.btLogin.setOnClickListener(this)
        binding.loginViaGoogle.setOnClickListener(this)
        binding.label.setOnClickListener(this)
        binding.labelRegister.setOnClickListener(this)

        binding.btRegister.setOnClickListener {
            registerUserDetails()
        }

    }

    private fun registerUserDetails() {

        val email = binding.etRegisterEmail.text.toString()
        val pwd = binding.etRegisterPassword.text.toString()
        val phoneNumber = binding.etPhoneNum.text.toString()

        when {
            email.isEmpty() -> {
                Toast.makeText(this, "email not found", Toast.LENGTH_SHORT).show()
            }

            pwd.isEmpty() -> {
                Toast.makeText(this, "password not found", Toast.LENGTH_SHORT).show()
            }

            phoneNumber.isEmpty() -> {
                Toast.makeText(this, "phone num not found", Toast.LENGTH_SHORT).show()
            }

            else -> {
                registerUser(email, pwd, phoneNumber)

            }
        }
    }

    private fun loginUser() {

        val email = binding.etEmail.text.toString()
        val pwd = binding.etPassword.text.toString()

        when {
            email.isEmpty() -> {
                Toast.makeText(this, "email not found", Toast.LENGTH_SHORT).show()
            }

            pwd.isEmpty() -> {
                Toast.makeText(this, "password not found", Toast.LENGTH_SHORT).show()
            }

            else -> {
                authenticateUser(email, pwd)
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btLogin -> {
                loginUser()
            }

            R.id.loginViaGoogle -> {

            }

            R.id.label -> {
                binding.llRegister.visibility = View.GONE
                binding.view2.visibility = View.GONE
                binding.llLogin.visibility = View.VISIBLE
                binding.view1.visibility = View.VISIBLE
            }

            R.id.labelRegister -> {
                binding.llRegister.visibility = View.VISIBLE
                binding.view2.visibility = View.VISIBLE
                binding.llLogin.visibility = View.GONE
                binding.view1.visibility = View.GONE
            }
        }
    }

    private fun authenticateUser(email: String, pass: String) {
        runCatching {
            binding.loader.visibility = View.VISIBLE
            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.loader.visibility = View.GONE
                    val currentUser = firebaseAuth.currentUser
                    Log.d(
                        LUX_TAG,
                        "signInWithEmail:success == ${currentUser?.email} ${currentUser?.phoneNumber}"
                    )
                    LuxoraPreferences().put(this,"isLogin",true)
                    gotoActivity(this, HomeActivity::class.java)
                } else {
                    showMessage(this@LoginActivity, "No user found")
                }
            }
        }.onFailure { exception ->
            binding.loader.visibility = View.GONE
            Log.i(LUX_TAG, "exception --> ${exception.message}")
        }
    }

    private fun registerUser(email: String, pwd: String, phoneNum: String) {
        runCatching {
            val user = hashMapOf("email" to email, "password" to pwd, "phoneNum" to phoneNum)
            firebaseDb.collection("users").add(user).addOnSuccessListener {
                Toast.makeText(this, "user added to database", Toast.LENGTH_SHORT).show()
                gotoActivity(this@LoginActivity, LoginActivity::class.java)
                finish()

            }.addOnFailureListener {

                DialogLoader(this).dismissDialog()
                showMessage(this, "user not registered")

            }
        }.onFailure { exception ->
            Log.i(LUX_TAG, "exception --> ${exception.message}")
        }

    }

}