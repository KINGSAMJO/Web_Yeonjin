package com.example.soptseminar1.util

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun Int.pxToDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

class MyItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val offset = 10
        offset.pxToDp()
        outRect.set(offset, offset, offset, offset)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val dividerWidth = 1
        dividerWidth.pxToDp()
        val paint = Paint()
        paint.color = Color.GRAY

        val left = parent.paddingStart.toFloat()
        val right = (parent.width - parent.paddingEnd).toFloat()

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val param = child.layoutParams as RecyclerView.LayoutParams
            val dividerTop = child.bottom + param.bottomMargin + 10
            val dividerBottom = dividerTop + dividerWidth
            c.drawRect(left, dividerTop.toFloat(), right, dividerBottom.toFloat(), paint)
        }
    }

}