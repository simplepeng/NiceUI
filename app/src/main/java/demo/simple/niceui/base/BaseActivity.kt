package demo.simple.niceui.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    protected lateinit var mContext: Context

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initViewBinding()
        setContentView(binding.root)

        mContext = this
        supportActionBar?.run {
            title = mContext.javaClass.simpleName
        }
        initView()
    }

    abstract fun initViewBinding(): T

    abstract fun initView()
}