package com.example.myshop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myshop.databinding.FragmentAccountBinding

class AccountFragment : BaseFragment(R.layout.fragment_account) {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAccountBinding.bind(view)




    }


}
