package com.wolfsea.pulltorefreshdemo.logger
import android.util.Log

/**
 *@desc  日志打印
 *@author liuliheng
 *@time 2021/6/27  23:05
 **/

fun log(message: String) {
    Log.d(TAG, message)
}


const val TAG = "pull_to_refresh"

