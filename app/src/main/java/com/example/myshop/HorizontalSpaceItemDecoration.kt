package com.example.myshop

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalSpaceItemDecoration(private val spaceWidth: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        // Добавляем отступ слева только для ПЕРВОГО элемента, чтобы он совпал с сеткой экрана
        if (position == 0) {
            outRect.left = spaceWidth
        }

        // Добавляем отступ справа для ВСЕХ элементов (расстояние между ними)
        outRect.right = spaceWidth

        // Для последнего элемента делаем отступ справа побольше, чтобы он не прилипал при скролле
        if (position == itemCount - 1) {
            outRect.right = spaceWidth
        }
    }
}


