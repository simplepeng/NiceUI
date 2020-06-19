package demo.simple.niceui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutRes())

        mContext = this
        supportActionBar?.run {
            title = mContext.javaClass.simpleName
        }
        initView()
    }

    abstract fun setLayoutRes(): Int

    abstract fun initView()
}