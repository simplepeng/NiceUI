package demo.simple.niceui.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

const val TAG = "NiceUI"

fun debugLog(msg: String) {
    Log.d(TAG, msg)
}

fun Context.showToast(text: String?) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}