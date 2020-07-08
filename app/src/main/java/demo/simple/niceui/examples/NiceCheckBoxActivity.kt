package demo.simple.niceui.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import demo.simple.niceui.R
import demo.simple.niceui.utils.logD
import kotlinx.android.synthetic.main.activity_nice_checkbox.*

class NiceCheckBoxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nice_checkbox)

        checkbox1.setOnCheckedListener { checkBox, isChecked ->
            logD("isChecked == $isChecked")
        }

        checkbox2.setOnCheckedListener { checkBox, isChecked ->
            logD("isChecked == $isChecked")
            checkBox.isChecked = !isChecked
        }
    }
}