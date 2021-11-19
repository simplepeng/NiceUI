package demo.simple.niceui.examples

import demo.simple.niceui.base.BaseActivity
import demo.simple.niceui.databinding.ActivityDashLineBinding

class DashLineViewActivity : BaseActivity<ActivityDashLineBinding>() {

    override fun initViewBinding() = ActivityDashLineBinding.inflate(this.layoutInflater)

    override fun initView() {
    }
}