package com.example.myshop

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.example.myshop.databinding.FragmentProductDetailBinding


class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProductDetailBinding.bind(view)


        val productId = arguments?.getString("productId")

        val product = productId?.let {
            ProductStore.findById(it)
        }

        if (product == null) {
            findNavController().popBackStack()
            return
        }

        binding.tvProductTitle.text = product.title
        binding.tvProductWeight.text = product.weight
        binding.tvProductPrice.text = product.price
        binding.ivPicture.setImageResource(product.imageRes)


        binding.btnBackToFirstFragment.setOnClickListener {
            findNavController().popBackStack()
        }

        var isDescriptionExpanded: Boolean = false

        binding.btnToggleDescription.setOnClickListener {
            isDescriptionExpanded = !isDescriptionExpanded

            if (isDescriptionExpanded) {
                binding.tvProductDescription.visibility = View.VISIBLE
                binding.btnToggleDescription.rotation = 90f
            } else {
                binding.tvProductDescription.visibility = View.GONE
                binding.btnToggleDescription.rotation = 0f
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

