package com.example.myshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshop.databinding.FragmentFavouriteBinding

class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    val cartItems = listOf(
        FavouriteCategory(
            imageRes = R.drawable.pepper_picture,
            title = "Bell Pepper Red",
            weight = "1kg, Price",
            price = "$4.99"
        ),
        FavouriteCategory(
            imageRes = R.drawable.apple_picture,
            title = "Egg Chicken Red",
            weight = "4pcs, Price",
            price = "$1.99"
        ),
        FavouriteCategory(
            imageRes = R.drawable.banana_picture,
            title = "Organic Bananas",
            weight = "12kg, Price",
            price = "$3.00"
        ),
        FavouriteCategory(
            imageRes = R.drawable.apple_picture,
            title = "Ginger",
            weight = "250gm, Price",
            price = "$2.99"
        ),
        FavouriteCategory(
            imageRes = R.drawable.pepper_picture,
            title = "Red Apple",
            weight = "1kg, Price",
            price = "$4.99"
        ),
        FavouriteCategory(
            imageRes = R.drawable.banana_picture,
            title = "Banana Pack",
            weight = "7pcs, Price",
            price = "$3.49"
        ),
        FavouriteCategory(
            imageRes = R.drawable.apple_picture,
            title = "Fresh Apple",
            weight = "1kg, Price",
            price = "$2.79"
        ),

        FavouriteCategory(
            imageRes = R.drawable.apple_picture,
            title = "Fresh Apple",
            weight = "1kg, Price",
            price = "$2.79"
        ),

        FavouriteCategory(
            imageRes = R.drawable.apple_picture,
            title = "Fresh Apple",
            weight = "1kg, Price",
            price = "$2.79"
        ),

        FavouriteCategory(
            imageRes = R.drawable.apple_picture,
            title = "Fresh Apple",
            weight = "1kg, Price",
            price = "$2.79"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)
        return (view)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        _binding = FragmentFavouriteBinding.bind(view)

        binding.rvProductsCart.apply {
            adapter = FavouriteBannerAdapter(cartItems)
            layoutManager = LinearLayoutManager(requireContext())



        }
    }


}
