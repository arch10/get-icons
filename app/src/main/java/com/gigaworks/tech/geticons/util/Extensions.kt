package com.gigaworks.tech.geticons.util

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment

fun Fragment.getClassName(): String {
    return this.javaClass.simpleName
}

fun Fragment.logD(msg: String?) {
    Log.d(TAG, "${this.getClassName()}: $msg")
}

fun View.visible(visible: Boolean) {
    this.visibility = if(visible) View.VISIBLE else View.GONE
}