package com.example.myshop.features.shop.ui

import android.os.Bundle
import android.view.View
import com.example.myshop.R
import com.example.myshop.app.BaseFragment
import com.example.myshop.databinding.FragmentShopLocationMapBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.runtime.image.ImageProvider
import java.lang.ref.WeakReference

class ShopLocationMapFragment : BaseFragment(R.layout.fragment_shop_location_map) {

    private var _binding: FragmentShopLocationMapBinding? = null
    private val binding get() = _binding!!
    private val shopPlacemarkTapListener = MapObjectTapListener { _, _ ->
        showShopLocationBottomSheet()
        true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentShopLocationMapBinding.bind(view)

        moveCameraToShop()
        addShopPlacemark()
    }

    private fun moveCameraToShop() {
        binding.mapView.mapWindow.map.move(
            CameraPosition(
                Point(LATITUDE, LONGITUDE),
                MAP_ZOOM,
                MAP_AZIMUTH,
                MAP_TILT
            )
        )
    }

    private fun addShopPlacemark() {
        binding.mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = Point(LATITUDE, LONGITUDE)
            setIcon(
                ImageProvider.fromResource(
                    requireContext(),
                    R.drawable.ic_butcher_shop_marker
                )
            )
            addTapListener(WeakReference(shopPlacemarkTapListener))
        }
    }

    private fun showShopLocationBottomSheet() {
        if (parentFragmentManager.findFragmentByTag(ShopLocationBottomSheetFragment.TAG) == null) {
            ShopLocationBottomSheetFragment().show(
                parentFragmentManager,
                ShopLocationBottomSheetFragment.TAG
            )
        }
    }

    override fun onStart() {
        super.onStart()

        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()

        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private companion object {
        const val LATITUDE = 53.302031
        const val LONGITUDE = 28.621735
        const val MAP_ZOOM = 18.0f
        const val MAP_AZIMUTH = 0.0f
        const val MAP_TILT = 0.0f
    }
}
