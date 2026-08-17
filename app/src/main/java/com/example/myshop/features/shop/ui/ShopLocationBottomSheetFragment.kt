package com.example.myshop.features.shop.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.myshop.R
import com.example.myshop.databinding.BottomSheetShopLocationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.core.net.toUri

class ShopLocationBottomSheetFragment : BottomSheetDialogFragment(R.layout.bottom_sheet_shop_location) {

    private var _binding: BottomSheetShopLocationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = BottomSheetShopLocationBinding.bind(view)

        binding.btnCloseShopLocation.setOnClickListener {
            dismiss()
        }
        binding.btnBuildRoute.setOnClickListener {
            openRouteToShop()
        }
    }

    private fun openRouteToShop() {
        val navigatorUri = "yandexnavi://build_route_on_map".toUri()
            .buildUpon()
            .appendQueryParameter("lat_to", SHOP_LATITUDE.toString())
            .appendQueryParameter("lon_to", SHOP_LONGITUDE.toString())
            .build()

        val navigatorIntent = Intent(Intent.ACTION_VIEW, navigatorUri)
            .setPackage(YANDEX_NAVIGATOR_PACKAGE)

        try {
            startActivity(navigatorIntent)
        } catch (_: ActivityNotFoundException) {
            openFallbackMap()
        }
    }

    private fun openFallbackMap() {
        val mapUri = "https://yandex.by/maps/".toUri()
            .buildUpon()
            .appendQueryParameter("rtext", "~$SHOP_LATITUDE,$SHOP_LONGITUDE")
            .appendQueryParameter("rtt", "auto")
            .build()

        try {
            startActivity(Intent(Intent.ACTION_VIEW, mapUri))
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(requireContext(), R.string.route_app_not_found, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ShopLocationBottomSheetFragment"
        private const val SHOP_LATITUDE = 53.302031
        private const val SHOP_LONGITUDE = 28.621735
        private const val YANDEX_NAVIGATOR_PACKAGE = "ru.yandex.yandexnavi"
    }
}
