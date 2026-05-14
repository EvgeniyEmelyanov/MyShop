package com.example.myshop

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myshop.app.BaseFragment

class FilterFragment : BaseFragment(R.layout.fragment_filter) {

    private val vm: FilterViewModel by viewModels ()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}