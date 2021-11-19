package demo.simple.niceui.examples

import demo.simple.niceui.base.BaseActivity
import demo.simple.niceui.databinding.ActivitySquareLayoutBinding

class SquareLayoutActivity : BaseActivity<ActivitySquareLayoutBinding>() {

    override fun initViewBinding() = ActivitySquareLayoutBinding.inflate(this.layoutInflater)

    override fun initView() {
    }
}