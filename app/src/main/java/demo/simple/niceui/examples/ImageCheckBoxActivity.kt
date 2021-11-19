package demo.simple.niceui.examples

import demo.simple.niceui.base.BaseActivity
import demo.simple.niceui.databinding.ActivityNiceCheckboxBinding
import demo.simple.niceui.utils.showToast

class ImageCheckBoxActivity : BaseActivity<ActivityNiceCheckboxBinding>() {

    override fun initViewBinding() = ActivityNiceCheckboxBinding.inflate(this.layoutInflater)

    override fun initView() {
        binding.run {
            checkbox1.setOnCheckedListener { checkBox, isChecked ->
                showToast("isChecked == $isChecked")
            }

            checkbox2.setOnCheckedListener { checkBox, isChecked ->
                showToast("isChecked == ${!isChecked}")
                checkBox.isChecked = !isChecked
            }
        }

    }
}