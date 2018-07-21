package com.turlir.abakcalc

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.view.View

class SpaceDecorator : RecyclerView.ItemDecoration {

    private val mLeft: Int
    private val mTop: Int
    private val mRight: Int
    private val mBottom: Int

    /**
     * @param cnt контекст
     * @param all отступ для всех сторон
     */
    constructor(cnt: Context, @DimenRes all: Int) : this(cnt, all, all, all, all)

    /**
     * @param all отступ для всех сторон
     */
    constructor(all: Int) : this(all, all, all, all)

    /**
     * @param cnt контекст
     * @param side отступ с боков
     * @param front отступ сверху и снизу
     */
    constructor(cnt: Context, @DimenRes side: Int, @DimenRes front: Int) : this(cnt, side, front, side, front)

    /**
     * @param cnt контекст
     * @param left отступ слева
     * @param top отступ сверху
     * @param right отступ справа
     * @param bottom отступ снизу
     */
    constructor(cnt: Context, @DimenRes left: Int, @DimenRes top: Int, @DimenRes right: Int, @DimenRes bottom: Int) {
        mLeft = cnt.resources.getDimension(left).toInt()
        mTop = cnt.resources.getDimension(top).toInt()
        mRight = cnt.resources.getDimension(right).toInt()
        mBottom = cnt.resources.getDimension(bottom).toInt()
    }

    /**
     * @param left отступ слева
     * @param top отступ сверху
     * @param right отступ справа
     * @param bottom отступ снизу
     */
    constructor(left: Int, top: Int, right: Int, bottom: Int) {
        mLeft = left
        mTop = top
        mRight = right
        mBottom = bottom
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val index = parent.getChildLayoutPosition(view)
        if (index != RecyclerView.NO_POSITION) {
            view.setPadding(
                    mLeft,
                    mTop,
                    mRight,
                    mBottom
            )
        }
    }

}