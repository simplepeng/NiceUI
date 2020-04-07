package me.simple.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View

class HorizontalProgressBar : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    var radius: Int = 0

    private val backgroundDrawable = GradientDrawable().apply {
//        gradientType = GradientDrawable.LINEAR_GRADIENT
//        shape = GradientDrawable.RECTANGLE
        setStroke(1.dp, Color.BLACK)
    }
    private val progressBarDrawable = GradientDrawable()
    private val secondProgressBarDrawable = GradientDrawable()

    var progress: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    var max: Int = 100

    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs == null) return
        val attr =
            context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar)

        backgroundDrawable.setColor(
            attr.getColor(
                R.styleable.HorizontalProgressBar_backgroundColor,
                Color.BLACK
            )
        )
        progressBarDrawable.setColor(
            attr.getColor(
                R.styleable.HorizontalProgressBar_progressColor,
                Color.WHITE
            )
        )
        secondProgressBarDrawable.setColor(
            attr.getColor(
                R.styleable.HorizontalProgressBar_secondProgressColor,
                Color.GRAY
            )
        )

        progress = attr.getInt(R.styleable.HorizontalProgressBar_progress, 0)
        max = attr.getInt(R.styleable.HorizontalProgressBar_max, 100)


        attr.recycle()
    }

    init {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        backgroundDrawable.setSize(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        val progressW = width / max * progress
        progressBarDrawable.setSize(progressW, height)

        backgroundDrawable.draw(canvas)
//        secondProgressBarDrawable.draw(canvas)
//        progressBarDrawable.draw(canvas)
    }
}