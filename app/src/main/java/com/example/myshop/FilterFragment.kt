package com.example.myshop

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment

class FilterFragment : Fragment(R.layout.fragment_blank) {

    companion object {
        fun newInstance() = FilterFragment()
    }

    private val viewModel: BlankViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }


}