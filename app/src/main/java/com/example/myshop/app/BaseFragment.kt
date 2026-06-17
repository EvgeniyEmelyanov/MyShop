package com.example.myshop.app

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.core.ui.dpToPx

open class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    // Метод для настройки отступов для всего содержимого
    protected fun setInsetsForFragment(view: View, additionalTopMarginDp: Int = 0, additionalBottomMarginDp: Int = 0) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                top = systemBars.top + requireContext().dpToPx(additionalTopMarginDp),
                bottom = requireContext().dpToPx(additionalBottomMarginDp)
            )

            insets
        }
        view.requestApplyInsets()
    }

    // Метод для настройки отступов для конкретных элементов
    protected fun setInsetsForView(view: View, additionalTopMarginDp: Int = 0, additionalBottomMarginDp: Int = 0, clipToPadding: Boolean = false) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                top = systemBars.top + requireContext().dpToPx(additionalTopMarginDp),
                bottom = requireContext().dpToPx(additionalBottomMarginDp)
            )

            // Настроить clipToPadding, чтобы элементы могли проходить через padding
            if (view is RecyclerView) {
                view.clipToPadding = clipToPadding
            }

            insets
        }
        view.requestApplyInsets()
    }
}
