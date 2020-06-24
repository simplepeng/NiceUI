package demo.simple.niceui.examples

import demo.simple.niceui.BaseActivity
import demo.simple.niceui.R
import kotlinx.android.synthetic.main.activity_badge.*

class BadgeViewActivity : BaseActivity(){

    override fun setLayoutRes(): Int {
        return R.layout.activity_badge
    }

    override fun initView() {
        badgeView1.text = "1"
        badgeView2.text = "22"
        badgeView3.text = "333"
    }
}