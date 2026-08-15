package com.zdevlab.luxora.screens.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zdevlab.luxora.databinding.FragmentCartBinding
import com.zdevlab.luxora.screens.adapter.CartAdapter
import com.zdevlab.luxora.screens.viewmodel.LuxoraViewModel
import com.zdevlab.luxora.R
import com.zdevlab.luxora.gotoActivity
import com.zdevlab.luxora.logMessage
import com.zdevlab.luxora.screens.activity.CartCheckoutActivity
import com.zdevlab.luxora.screens.models.CartItemModel
import com.zdevlab.luxora.showMessage

class CartFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: LuxoraViewModel
    private var cartArrayList = ArrayList<CartItemModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews(){

        binding.cvCheckOut.setOnClickListener(this)
        initViewModel()
        getCartList()
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[LuxoraViewModel::class.java]
    }

    private fun getCartList(){

        val cartItem = viewModel.cartItems.value

        if(cartItem?.size == 0) {
            binding.tvItemCount.text = "0 Items curated for you"
        }
        else {
            binding.tvItemCount.text= "${cartItem?.size} Items curated for you"
        }

            if(cartItem != null && cartItem.isNotEmpty()){

                binding.llScrollMain.visibility = View.VISIBLE
                binding.llNoItemFound.visibility = View.GONE

                viewModel.cartItems.observe(viewLifecycleOwner){ items ->
                    binding.rvCart.apply {
                        adapter = CartAdapter(
                            this.context,
                            items,
                            viewModel
                        )
                        layoutManager = LinearLayoutManager(this.context)
                    }

                    var totalAmount = 0
                    for(i in items){
                        totalAmount += i.productAmount.replace("$","").trim().toDouble().toInt()
                        cartArrayList.add(i)
                    }
                }
                with(binding){
                    viewModel.savedQuantity.observe(viewLifecycleOwner) { data ->
                        binding.tvAmountSubTotal.text = data.toString()
                        tvAmountShipping.text = "$25"
                        tvAmountTax.text = "$10"
                        val basePrice = viewModel.productBasePrice.replace("$","").trim().toDouble().toInt()
                        tvAmount.text = "$${(tvAmountSubTotal.text.toString().toInt() * basePrice) + 25 + 10}"
                    }
                }

        }
        else{
            binding.llScrollMain.visibility = View.GONE
            binding.llNoItemFound.visibility = View.VISIBLE
        }

    }

    override fun onClick(view: View?) {
        when(view?.id){

            R.id.cvCheckOut->{
                if (binding.tvAmountSubTotal.text.toString().toInt() > 0){
                    gotoActivity(
                        requireActivity() ,
                        "isComingFromCart" ,"1",
                        "cartList", cartArrayList,
                        CartCheckoutActivity::class.java
                    )
                }else{
                    showMessage(requireActivity(), "cart is empty")
                }

            }

        }
    }

}