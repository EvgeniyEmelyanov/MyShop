package com.example.myshop

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.FragmentCartBinding
import com.evgeniyemelyanov.core.ui.dpToPx



class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!


    val cartItems = listOf(
        CartCategory(
            imageRes = R.drawable.pepper_picture,
            title = "Bell Pepper Red",
            weight = "1kg, Price",
            count = "1",
            price = "$4.99"
        ),
        CartCategory(
            imageRes = R.drawable.apple_picture,
            title = "Egg Chicken Red",
            weight = "4pcs, Price",
            count = "1",
            price = "$1.99"
        ),
        CartCategory(
            imageRes = R.drawable.banana_picture,
            title = "Organic Bananas",
            weight = "12kg, Price",
            count = "1",
            price = "$3.00"
        ),
        CartCategory(
            imageRes = R.drawable.apple_picture,
            title = "Ginger",
            weight = "250gm, Price",
            count = "1",
            price = "$2.99"
        ),
        CartCategory(
            imageRes = R.drawable.pepper_picture,
            title = "Red Apple",
            weight = "1kg, Price",
            count = "1",
            price = "$4.99"
        ),
        CartCategory(
            imageRes = R.drawable.banana_picture,
            title = "Banana Pack",
            weight = "7pcs, Price",
            count = "1",
            price = "$3.49"
        ),
        CartCategory(
            imageRes = R.drawable.apple_picture,
            title = "Fresh Apple",
            weight = "1kg, Price",
            count = "1",
            price = "$2.79"
        ),

        CartCategory(
            imageRes = R.drawable.apple_picture,
            title = "Fresh Apple",
            weight = "1kg, Price",
            count = "1",
            price = "$2.79"
        ),

        CartCategory(
            imageRes = R.drawable.apple_picture,
            title = "Fresh Apple",
            weight = "1kg, Price",
            count = "1",
            price = "$2.79"
        ),

        CartCategory(
            imageRes = R.drawable.apple_picture,
            title = "Fresh Apple",
            weight = "1kg, Price",
            count = "1",
            price = "$2.79"
        )

    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCartBinding.bind(view)

        binding.rvProductsCart.apply {
            adapter = CartBannerAdapter(cartItems)
            layoutManager = LinearLayoutManager(requireContext())

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    CartDividerDecoration(
                        context = requireContext(),
                        colorRes = R.color.line_for_products_banner,
                        heightPx = requireContext().dpToPx(1),
                        insetPx = requireContext().dpToPx(25),
                        skipLast = true
                    )
                )
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


