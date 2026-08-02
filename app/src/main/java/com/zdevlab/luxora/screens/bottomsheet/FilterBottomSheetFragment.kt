package com.zdevlab.luxora.screens.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zdevlab.luxora.R
import com.zdevlab.luxora.databinding.FragmentFilterBottomSheetBinding
import com.zdevlab.luxora.logMessage
import com.zdevlab.luxora.screens.viewmodel.LuxoraViewModel
import java.io.Serializable

class FilterBottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentFilterBottomSheetBinding
    private var productType = ""
    private var priceRange = 0

    var onDataSubmit : ((FilterModel) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews(){

        binding.tvShoes.setOnClickListener(this)
        binding.tvOthers.setOnClickListener(this)
        binding.tvWatches.setOnClickListener(this)
        binding.btApplyFilter.setOnClickListener(this)

        binding.slider.addOnChangeListener { slider, value, fromUser ->
            priceRange = value.toInt()
        }

    }

    private fun changeProductType(watch: Boolean, shoes:Boolean, others: Boolean){
        with(binding){
            when{
                watch == true->{
                    tvWatches.setBackgroundResource(R.drawable.light_grey_stroke_yellow_rounded_bg)
                    tvShoes.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
                    tvOthers.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
                }
                shoes == true->{
                    tvWatches.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
                    tvShoes.setBackgroundResource(R.drawable.light_grey_stroke_yellow_rounded_bg)
                    tvOthers.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
                }
                others == true->{
                    tvWatches.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
                    tvShoes.setBackgroundResource(R.drawable.light_grey_stroke_black_rounded_bg)
                    tvOthers.setBackgroundResource(R.drawable.  light_grey_stroke_yellow_rounded_bg)
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tvShoes ->{
                productType = "shoes"
                changeProductType(false, true, false)
            }
            R.id.tvWatches ->{
                productType = "watches"
                changeProductType(true, false, false)
            }
            R.id.tvOthers ->{
                productType = "others"
                changeProductType(false, false, true)
            }
            R.id.btApplyFilter->{
                if (priceRange != 0 && productType.isNotEmpty()){
                    onDataSubmit?.invoke(FilterModel(priceRange, productType))
                    dismiss()
                }
            }
        }
    }

}

data class FilterModel(
    var priceRange:Int,
    var productType:String,
): Serializable