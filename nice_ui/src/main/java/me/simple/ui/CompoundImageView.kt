package me.simple.ui

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView

/**
 * 用图片做切换的CheckBox，Switch,RadioButton基类
 */
open class CompoundImageView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    //是否自动选中
    var autoChecked: Boolean = true

    //是否选中
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

    /**
     * 设置切换的图片资源
     */
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

    /**
     * 设置选中监听
     */
    fun setOnCheckedListener(onChecked: (checkBox: CompoundImageView, isChecked: Boolean) -> Unit) {
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