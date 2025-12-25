package com.example.myshop

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CartDividerDecoration(
    context: Context,
    private val colorRes: Int,
    private val heightPx: Int,
    private val leftPaddingPx: Int,
    private val rightPaddingPx: Int,
    private val skipLast: Boolean = true
) : RecyclerView.ItemDecoration() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, colorRes)
        style = Paint.Style.FILL
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        val lastIndex = if (skipLast) childCount - 1 else childCount

        for (i in 0 until lastIndex) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val left = parent.paddingLeft + leftPaddingPx
            val right = parent.width - parent.paddingRight - rightPaddingPx

            val top = child.bottom + params.bottomMargin
            val bottom = top + heightPx

            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

    override fun getItemOffsets(
        outRect: android.graphics.Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // место под линию
        outRect.bottom = heightPx
    }
}
