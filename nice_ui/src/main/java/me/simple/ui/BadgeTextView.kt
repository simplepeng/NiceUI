package me.simple.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView

class BadgeTextView @JvmOverloads constructor(
    context: Context? = null,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val drawable = GradientDrawable()

    var color: Int = Color.RED
        set(value) {
            field = value
            requestLayout()
        }
    var horizontalPadding = 0
        set(value) {
            field = value
            requestLayout()
        }
    var verticalPadding = 0
        set(value) {
            field = value
            requestLayout()
        }

    var maxCount = Int.MAX_VALUE

    init {
        gravity = Gravity.CENTER
        isSingleLine = true
        maxLines = 1
        initAttrs(context, attrs, defStyleAttr)
    }

    private fun initAttrs(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) {
        if (context == null || attrs == null) return
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BadgeTextView)
        color = typedArray.getColor(R.styleable.BadgeTextView_color, Color.RED)
        horizontalPadding =
            typedArray.getDimension(R.styleable.BadgeTextView_horizontalPadding, 8f.dp).toInt()
        verticalPadding =
            typedArray.getDimension(R.styleable.BadgeTextView_verticalPadding, 0f).toInt()
        maxCount = typedArray.getInt(R.styleable.BadgeTextView_maxCount, Int.MAX_VALUE)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

//        val square = measuredWidth <= measuredHeight
        val square = text.length == 1
        if (square) {
            setMeasuredDimension(measuredHeight, measuredHeight)
        } else {
            setMeasuredDimension(
                measuredWidth + (horizontalPadding * 2),
                measuredHeight + (verticalPadding * 2)
            )
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        if (text.isNullOrEmpty()) {
            super.setText(text, type)
            return
        }
        if (text.length > 1 && maxCount > 1 && text.length > maxCount) {
            val realText = text.subSequence(0, maxCount).toString() + "+"
            super.setText(realText, type)
            return
        }

        super.setText(text, type)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setDrawable(h)
    }

    override fun onDraw(canvas: Canvas?) {
        setDrawable(height)
        super.onDraw(canvas)
    }

    private fun setDrawable(height: Int) {
        drawable.cornerRadius = height / 2f
        drawable.setColor(color)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            background = drawable
        } else {
            setBackgroundDrawable(drawable)
        }
    }
}