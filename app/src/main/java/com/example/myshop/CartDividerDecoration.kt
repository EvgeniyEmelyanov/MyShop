package com.example.myshop

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CartDividerDecoration(
    context: Context,
    colorRes: Int,
    private val heightPx: Int,
    private val insetPx: Int,          // ← один параметр: 25dp
    private val skipLast: Boolean = true
) : RecyclerView.ItemDecoration() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, colorRes)
        style = Paint.Style.FILL
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter ?: return
        val lastPos = adapter.itemCount - 1

        val left = insetPx
        val right = parent.width - insetPx

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val pos = parent.getChildAdapterPosition(child)
            if (pos == RecyclerView.NO_POSITION) continue
            if (skipLast && pos == lastPos) continue

            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + heightPx

            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter ?: return
        val lastPos = adapter.itemCount - 1

        val pos = parent.getChildAdapterPosition(view)
        if (pos == RecyclerView.NO_POSITION) return

        outRect.bottom = if (skipLast && pos == lastPos) 0 else heightPx
    }
}
