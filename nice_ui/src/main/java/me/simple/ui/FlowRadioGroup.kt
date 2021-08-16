package me.simple.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RadioGroup

/**
 * 流式/自动换行的RadioGroup
 */
class FlowRadioGroup : RadioGroup {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var lineWidth = 0
        var lineHeight = 0
        var heightSize = 0

        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val count = childCount
        var childView: View
        for (i in 0 until count) {
            childView = getChildAt(i)
            if (childView.visibility == GONE) {
                continue
            }
            val params = childView.layoutParams as MarginLayoutParams
            val childWidth = childView.measuredWidth + params.leftMargin + params.rightMargin
            val childHeight = childView.measuredHeight + params.topMargin + params.bottomMargin
            if (lineWidth + childWidth > widthSize - paddingRight - paddingLeft) { //要换行了
                lineWidth = childWidth
                heightSize += lineHeight
                lineHeight = childHeight
            } else {
                lineWidth += childWidth
                lineHeight = Math.max(lineHeight, childHeight)
            }
            if (i == count - 1) {
                heightSize += lineHeight
            }
        }

        heightSize = if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            MeasureSpec.getSize(heightMeasureSpec)
        } else {
            heightSize + paddingTop + paddingBottom
        }
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        val width = width
        var lineWidth = 0
        var lineHeight = 0
        var heightSize = 0
        var childView: View
        for (i in 0 until count) {
            childView = getChildAt(i)
            if (childView.visibility == GONE) {
                continue
            }
            val params = childView.layoutParams as MarginLayoutParams
            val childWidth = childView.measuredWidth
            val childHeight = childView.measuredHeight
            if (lineWidth + childWidth + params.leftMargin + params.rightMargin >
                width - paddingLeft - paddingRight
            ) { //要换行了
                heightSize += lineHeight
                lineWidth = 0
            } else {
                lineHeight =
                    Math.max(lineHeight, childHeight + params.topMargin + params.bottomMargin)
            }
            val left = lineWidth + params.leftMargin
            val top = heightSize + params.topMargin
            val right = left + childWidth
            val bottom = top + childHeight
            childView.layout(left, top, right, bottom)
            lineWidth += childWidth + params.leftMargin + params.rightMargin
        }
    }
}