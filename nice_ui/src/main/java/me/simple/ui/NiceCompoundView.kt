package me.simple.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView

open class NiceCompoundView @JvmOverloads constructor(
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
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NiceCompoundView)
            unCheckedResId = typedArray.getResourceId(
                R.styleable.NiceCompoundView_unCheckedRes,
                R.drawable.ic_unchecked
            )
            checkedResId =
                typedArray.getResourceId(
                    R.styleable.NiceCompoundView_checkedRes,
                    R.drawable.ic_checked
                )
            isChecked = typedArray.getBoolean(R.styleable.NiceCompoundView_isChecked, false)
            autoChecked = typedArray.getBoolean(R.styleable.NiceCompoundView_autoChecked, true)
            typedArray.recycle()
        }
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

    fun setOnCheckedListener(onChecked: (checkBox: NiceCompoundView, isChecked: Boolean) -> Unit) {
        this.setOnClickListener {

            //不是自动选中
            if (!autoChecked) {
                onChecked.invoke(this, isChecked)
                return@setOnClickListener
            }

            //自动选中
            this.isChecked = !isChecked
            onChecked.invoke(this, isChecked)
        }

    }
}