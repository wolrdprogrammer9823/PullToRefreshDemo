package com.wolfsea.pulltorefreshdemo.extendmethod
import android.content.Context
import android.widget.Toast

/**
 *@desc  扩展方法
 *@author liuliheng
 *@time 2021/6/25  1:01
 **/

//Toast
fun Context?.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) = this?.let {

    Toast.makeText(it, text, duration).show()
}