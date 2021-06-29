package demo.simple.niceui.examples

import demo.simple.niceui.R
import demo.simple.niceui.base.BaseActivity
import demo.simple.niceui.utils.showToast
import kotlinx.android.synthetic.main.activity_nice_checkbox.*

class ImageCheckBoxActivity : BaseActivity() {

    override fun setLayoutRes()= R.layout.activity_nice_checkbox

    override fun initView() {
        checkbox1.setOnCheckedListener { checkBox, isChecked ->
            showToast("isChecked == $isChecked")
        }

        checkbox2.setOnCheckedListener { checkBox, isChecked ->
            showToast("isChecked == $isChecked")
            checkBox.isChecked = !isChecked
        }
    }
}