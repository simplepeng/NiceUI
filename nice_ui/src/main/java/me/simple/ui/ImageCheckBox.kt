package me.simple.ui

import android.content.Context
import android.util.AttributeSet

/**
 * 用图片的CheckBox
 */
class ImageCheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CompoundImageView(context, attrs, defStyleAttr)