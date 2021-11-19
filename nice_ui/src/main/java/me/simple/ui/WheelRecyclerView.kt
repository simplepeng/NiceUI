package me.simple.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

open class WheelRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    var isLoop = false

    var maxItem = 3

    private val snapHelper = LinearSnapHelper()

    init {
        snapHelper.attachToRecyclerView(this)

        if (isInEditMode) {
            isLoop = true
            val items = mutableListOf<String>()
            for (item in 1..10) {
                items.add(String.format("%02d", item))
            }
            setData(items, TextViewDelegate())
        }
    }

    fun <T> setData(
        items: List<T>,
        delegate: ViewHolderDelegate<T>
    ) {
        layoutManager = WheelLayoutManager(context)
        adapter = WheelAdapter(items, delegate)
        if (isLoop) {
            post {
                val centerStartPosition = (Int.MAX_VALUE / 2) - (Int.MAX_VALUE / 2 % items.size) + 1
                scrollToPosition(centerStartPosition)
            }
        }
    }

    inner class WheelLayoutManager(
        context: Context
    ) : LinearLayoutManager(context) {

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

            val itemView = recycler.getViewForPosition(0)
            addView(itemView)
//            measureChildWithMargins(itemView, 0, 0)
            measureChild(itemView, widthSpec, heightSpec)
            val itemWidth = getDecoratedMeasuredWidth(itemView)
            val itemHeight = getDecoratedMeasuredHeight(itemView)
            removeAndRecycleView(itemView, recycler)

            if (orientation == HORIZONTAL) {
                setMeasuredDimension(itemWidth * maxItem, itemHeight)
            } else {
                setMeasuredDimension(itemWidth, itemHeight * maxItem)
            }
        }
    }

    inner class WheelAdapter<T>(
        private val items: List<T>,
        private val delegate: ViewHolderDelegate<T>
    ) : RecyclerView.Adapter<ViewHolder>() {

        override fun getItemCount(): Int {
            if (isLoop) return Int.MAX_VALUE
            return items.size
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            return delegate.onCreateViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {
            val fixPosition = holder.adapterPosition % items.size
            val item = items[fixPosition]
            delegate.onBindViewHolder(holder, fixPosition, item)
        }

    }

    abstract class ViewHolderDelegate<T> {

        abstract fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder

        abstract fun onBindViewHolder(
            holder: ViewHolder,
            position: Int,
            item: T
        )
    }

    open class TextViewDelegate : ViewHolderDelegate<String>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_wheel_text_view, parent, false)
            return TextViewHolder(itemView)
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int,
            item: String
        ) {
            (holder.itemView as TextView).text = item
        }
    }

    open class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}