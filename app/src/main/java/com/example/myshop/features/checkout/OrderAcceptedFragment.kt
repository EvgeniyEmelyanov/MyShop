package com.example.myshop.features.checkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.databinding.FragmentOrderAcceptedBinding

class OrderAcceptedFragment : Fragment(R.layout.fragment_order_accepted) {

    private var _binding: FragmentOrderAcceptedBinding? = null
    private val binding get() = _binding!!



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentOrderAcceptedBinding.bind(view)

        binding.btnTrackOrder.setOnClickListener {
            Toast.makeText(requireContext(), R.string.coming_soon, Toast.LENGTH_SHORT).show()
        }

        binding.btnBackHome.setOnClickListener {
            findNavController().navigate(R.id.shopFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
