package demo.simple.niceui.examples

import android.view.View
import demo.simple.niceui.base.BaseActivity
import demo.simple.niceui.databinding.ActivityWheelRecyclerViewBinding
import demo.simple.niceui.utils.showToast
import me.simple.ui.WheelRecyclerView

class WheelRecyclerViewActivity : BaseActivity<ActivityWheelRecyclerViewBinding>() {

    override fun initViewBinding() = ActivityWheelRecyclerViewBinding.inflate(this.layoutInflater)

    override fun initView() {
        binding.WheelTextView.apply {
            isLoop = true
            val items = mutableListOf<String>()
            for (item in 0..20) {
                items.add(String.format("%02d", item))
            }
            setData(items, WheelRecyclerView.TextViewDelegate(), visibleCount = 1, isLoop = true)
            onItemSelectedListener = { position ->
                showToast(items[position])
            }
        }

    }

    fun getCurrentItem(view: View) {
        val currentItem = binding.WheelTextView.getCurrentItem()
        showToast("currentItem == $currentItem")
    }

    fun nextItem(view: View) {
        binding.WheelTextView.scrollToNext()
    }

    fun preItem(view: View) {
        binding.WheelTextView.scrollToPrevious()
    }
}