package com.zdevlab.luxora.screens.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.FragmentProfileBinding
import com.zdevlab.luxora.loadImage
import com.zdevlab.luxora.logErrorMessage
import com.zdevlab.luxora.screens.models.UserModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val firestore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews(){

        binding.loader.visibility = View.VISIBLE
        binding.llProfile.visibility = View.GONE

        firestore.collection("users").get().addOnSuccessListener { data->
            if (!data.isEmpty)
            {
                binding.loader.visibility = View.GONE
                binding.llProfile.visibility = View.VISIBLE

                val uModel = data.toObjects(UserModel::class.java)
                uModel.firstOrNull()?.apply {
                    loadImage(requireContext(), profileImg ?: "", binding.ivUser)
                    binding.tvEmail.text = email
                    binding.tvUserName.text = username
                    binding.tvPhoneNum.text = phoneNum
                    binding.tvAddress.text = primaryAddress
                    binding.tvCardNumber.text = cardNum
                    binding.tvCreated.text = "Member since $createdAt"
                    binding.tvEmail.text = email

                    when(userType){
                        1-> binding.tvUserType.text = "Elite Member"
                        2-> binding.tvUserType.text = "Elite Member"
                        3-> binding.tvUserType.text = "Elite Member"
                    }
                }
            }
        }.addOnFailureListener { e->

            binding.loader.visibility = View.GONE
            binding.llProfile.visibility = View.VISIBLE
            logErrorMessage("${e.message}")
        }

    }

}