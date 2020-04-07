package me.simple.ui

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView

class NiceCheckView : AppCompatImageView {

    var autoChecked: Boolean = true
    var isChecked: Boolean = false
        set(value) {
            changeStatus(value)
            field = value
        }

    private var unCheckedResId: Int = R.drawable.ic_uncheck
    private var checkedResId: Int = R.drawable.ic_checked

    constructor(context: Context) : super(context) {
        initAttrs(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NiceCheckView)
            unCheckedResId = typedArray.getResourceId(
                R.styleable.NiceCheckView_unCheckedRes,
                R.drawable.ic_uncheck
            )
            checkedResId = typedArray.getResourceId(
                R.styleable.NiceCheckView_checkedRes,
                R.drawable.ic_checked
            )
            isChecked = typedArray.getBoolean(R.styleable.NiceCheckView_isChecked, false)
            typedArray.recycle()
        }
        changeStatus(isChecked)
    }

    private fun changeStatus(isChecked: Boolean) {
        if (isChecked) {
            setImageResource(checkedResId)
        } else {
            setImageResource(unCheckedResId)
        }
    }

    private fun setResId(
        @DrawableRes
        unCheckedResId: Int,
        @DrawableRes
        checkedResId: Int
    ) {
        this.unCheckedResId = unCheckedResId
        this.checkedResId = checkedResId
        changeStatus(isChecked)
    }

    fun setOnCheckedListener(
        onChecked: (
            checkView: NiceCheckView,
            isChecked: Boolean
        ) -> Unit
    ) {
        this.setOnClickListener {
            if (!autoChecked) {
                onChecked.invoke(this, isChecked)
                return@setOnClickListener
            }

            this.isChecked = !isChecked
            onChecked.invoke(this, isChecked)
        }

    }
}