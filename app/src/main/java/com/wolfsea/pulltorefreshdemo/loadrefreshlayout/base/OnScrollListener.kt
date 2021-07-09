package com.wolfsea.pulltorefreshdemo.loadrefreshlayout.base
import android.widget.AbsListView

/**
 * 由于内部监听了OnScrollListener，导致外部监听滚动事件不起作用，采用接口方式将onScroll事件传出去
 */
interface OnScrollListener {
    fun onScroll(absListView: AbsListView?, i: Int, i1: Int, i2: Int)

    fun onScrollStateChanged(absListView: AbsListView?, i: Int)
}