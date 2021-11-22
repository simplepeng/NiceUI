package me.simple.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

open class WheelRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    var isLoop = false

    var visibleCount = 3

    var onItemSelectedListener: ((position: Int) -> Unit)? = null

    private val snapHelper = LinearSnapHelper()

    private var itemSize = 0

    init {
        snapHelper.attachToRecyclerView(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setInEditMode()
    }

    private fun setInEditMode() {
        if (isInEditMode) {
            isLoop = true
            val items = mutableListOf<String>()
            for (item in 1..100) {
                items.add(String.format("%02d", item))
            }
            setData(items, TextViewDelegate())
        }
    }

    fun <T> setData(
        items: List<T>,
        delegate: ViewHolderDelegate<T, *>,
        visibleCount: Int = 3,
        isLoop: Boolean = true,
        orientation: Int = LinearLayoutManager.VERTICAL,
        reverseLayout: Boolean = false
    ) {
        checkVisibleCount(visibleCount)

        this.itemSize = items.size
        this.isLoop = isLoop
        this.visibleCount = visibleCount

        layoutManager = WheelLayoutManager(context, orientation, reverseLayout)
        adapter = WheelAdapter(items, delegate)
        if (isLoop) {
            post {
                val centerStartPosition = (Int.MAX_VALUE / 2) - (Int.MAX_VALUE / 2 % items.size) + 1
                scrollToPosition(centerStartPosition)
            }
        }
    }

    private fun checkVisibleCount(visibleCount: Int) {
        if (visibleCount % 2 == 0)
            throw IllegalArgumentException("visibleCount不能是偶数")
    }

    inner class WheelLayoutManager(
        context: Context,
        orientation: Int,
        reverseLayout: Boolean
    ) : LinearLayoutManager(context, orientation, reverseLayout) {

        override fun isAutoMeasureEnabled(): Boolean {
            return false
        }

        override fun onMeasure(
            recycler: RecyclerView.Recycler,
            state: State,
            widthSpec: Int,
            heightSpec: Int
        ) {
            super.onMeasure(recycler, state, widthSpec, heightSpec)

            if (itemCount == 0) return

            val itemView = recycler.getViewForPosition(itemSize - 1)
            addView(itemView)
            measureChildWithMargins(itemView, 0, 0)
//            measureChild(itemView, widthSpec, heightSpec)

            val itemWidth = getDecoratedMeasuredWidth(itemView)
            val itemHeight = getDecoratedMeasuredHeight(itemView)

            val widthMode = MeasureSpec.getMode(widthSpec)
            val heightMode = MeasureSpec.getMode(heightSpec)

            if (widthMode == MeasureSpec.AT_MOST && orientation == VERTICAL) {
                throw IllegalArgumentException("竖向排列时-View的宽度不能是wrap_content")
            }

            if (heightMode == MeasureSpec.AT_MOST && orientation == HORIZONTAL) {
                throw IllegalArgumentException("横向排列时-View的高度不能是wrap_content")
            }

            var widthSize = MeasureSpec.getSize(widthSpec)
            var heightSize = MeasureSpec.getSize(heightSpec)

            if (orientation == HORIZONTAL) {
                widthSize = itemWidth * visibleCount
            } else {
                heightSize = itemHeight * visibleCount
            }

            removeAndRecycleView(itemView, recycler)

            setMeasuredDimension(widthSize, heightSize)
        }

        override fun generateDefaultLayoutParams(): LayoutParams {
            return if (orientation == HORIZONTAL) {
                LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            } else {
                LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
    }

    inner class WheelAdapter<T, VH : ViewHolder>(
        private val items: List<T>,
        private val delegate: ViewHolderDelegate<T, VH>
    ) : RecyclerView.Adapter<VH>() {

        override fun getItemCount(): Int {
            if (isLoop) return Int.MAX_VALUE
            return items.size
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): VH {
            return delegate.onCreateViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(
            holder: VH,
            position: Int
        ) {
            val fixPosition = getFixPosition(holder.adapterPosition)
            val item = items[fixPosition]
            delegate.onBindViewHolder(holder, fixPosition, item)
        }

    }

    fun getFixPosition(adapterPosition: Int): Int {
        return adapterPosition % itemSize
    }

    abstract class ViewHolderDelegate<T, VH : ViewHolder> {

        abstract fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): VH

        abstract fun onBindViewHolder(
            holder: VH,
            position: Int,
            item: T
        )
    }

    open class TextViewDelegate : ViewHolderDelegate<String, TextViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): TextViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_wheel_text_view, parent, false)
            return TextViewHolder(itemView)
        }

        override fun onBindViewHolder(
            holder: TextViewHolder,
            position: Int,
            item: String
        ) {
            (holder.itemView as TextView).text = item
        }
    }

    open class TextViewHolder(itemView: View) : ViewHolder(itemView)

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (state == SCROLL_STATE_IDLE) {
            onItemSelectedListener?.invoke(getFixPosition(getCurrentItem()))
        }
    }

    fun getCurrentItem(): Int {
        val centerView = snapHelper.findSnapView(this.layoutManager) ?: return -1
        val position = getChildAdapterPosition(centerView)
        return getFixPosition(position)
    }

    fun getCenterAdapterPosition(): Int {
        val centerView = snapHelper.findSnapView(this.layoutManager) ?: return -1
        return getChildAdapterPosition(centerView)
    }

    fun scrollToNext() {
        val position = getCenterAdapterPosition()
        if (position == -1 || adapter == null || adapter?.itemCount == 0) return
        val nextPosition = if (position == adapter!!.itemCount - 1) {
            0
        } else {
            position + (visibleCount / 2 + 1)
        }
        if (nextPosition == 0 && !isLoop) {
            scrollToPosition(nextPosition)
        } else {
            smoothScrollToPosition(nextPosition)
        }
    }

    fun scrollToPrevious() {
        val position = getCenterAdapterPosition()
        if (position == -1 || adapter == null || adapter?.itemCount == 0) return
        val prePosition = if (position == 0) {
            adapter!!.itemCount - 1
        } else {
            position - (visibleCount / 2 + 1)
        }
        if (prePosition == adapter!!.itemCount - 1 && !isLoop) {
            scrollToPosition(prePosition)
        } else {
            smoothScrollToPosition(prePosition)
        }
    }
}