package demo.simple.niceui.examples

import demo.simple.niceui.base.BaseActivity
import demo.simple.niceui.databinding.ActivityBadgeBinding

class BadgeViewActivity : BaseActivity<ActivityBadgeBinding>() {

    override fun initViewBinding() = ActivityBadgeBinding.inflate(this.layoutInflater)

    override fun initView() {
        binding.run {
            badgeView1.text = "1"
            badgeView2.text = "22"
            badgeView3.text = "333"
            badgeView5.text = "9999999"
        }
    }


}