package demo.simple.niceui.examples

import demo.simple.niceui.base.BaseActivity
import demo.simple.niceui.databinding.ActivityWheelRecyclerViewBinding
import me.simple.ui.WheelRecyclerView

class WheelRecyclerViewActivity : BaseActivity<ActivityWheelRecyclerViewBinding>() {

    override fun initViewBinding() = ActivityWheelRecyclerViewBinding.inflate(this.layoutInflater)

    override fun initView() {
        binding.WheelTextView.apply {
            isLoop = true
            val items = mutableListOf<String>()
            for (item in 0..123) {
                items.add(String.format("%02d", item))
            }
            setData(items, WheelRecyclerView.TextViewDelegate())
        }
    }
}