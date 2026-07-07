package com.example.myshop.features.checkout

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import com.example.myshop.R
import com.example.myshop.databinding.BottomSheetCheckoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CheckoutBottomSheetFragment : BottomSheetDialogFragment(R.layout.bottom_sheet_checkout) {

    private var _binding: BottomSheetCheckoutBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = BottomSheetCheckoutBinding.bind(view)

        val totalString = requireArguments().getString(ARG_TOTAL_STRING).orEmpty()
        binding.tvTotalCost.text = totalString

        binding.btnCloseCheckout.setOnClickListener {
            dismiss()
        }

        binding.btnPlaceOrder.setOnClickListener {
            setFragmentResult(
                CHECKOUT_RESULT_KEY,
                Bundle().apply {
                    putBoolean(CHECKOUT_CONFIRMED_KEY, true)
                }
            )
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "CheckoutBottomSheetFragment"
        const val CHECKOUT_RESULT_KEY = "checkout_result"
        const val CHECKOUT_CONFIRMED_KEY = "checkout_confirmed"
        private const val ARG_TOTAL_STRING = "arg_total_string"

        fun newInstance(totalString: String): CheckoutBottomSheetFragment {
            return CheckoutBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TOTAL_STRING, totalString)
                }
            }
        }
    }
}
