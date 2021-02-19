package me.simple.ui

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView

class NiceCheckBox @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    var autoChecked: Boolean = true

    var isChecked: Boolean = false
        set(value) {
            changeStatus(value)
            field = value
        }

    private var unCheckedResId: Int = R.drawable.ic_unchecked
    private var checkedResId: Int = R.drawable.ic_checked

    init {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NiceCheckBox)
            unCheckedResId = typedArray.getResourceId(R.styleable.NiceCheckBox_unCheckedRes, R.drawable.ic_unchecked)
            checkedResId = typedArray.getResourceId(R.styleable.NiceCheckBox_checkedRes, R.drawable.ic_checked)
            isChecked = typedArray.getBoolean(R.styleable.NiceCheckBox_isChecked, false)
            autoChecked = typedArray.getBoolean(R.styleable.NiceCheckBox_autoChecked, true)
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

    fun setOnCheckedListener(onChecked: (checkBox: NiceCheckBox, isChecked: Boolean) -> Unit) {
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