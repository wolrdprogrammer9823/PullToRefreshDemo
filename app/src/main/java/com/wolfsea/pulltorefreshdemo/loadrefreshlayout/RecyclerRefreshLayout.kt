package com.wolfsea.pulltorefreshdemo.loadrefreshlayout
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wolfsea.pulltorefreshdemo.loadrefreshlayout.base.RefreshLayout

/**
 *@desc  RecyclerRefreshLayout
 *@author liuliheng
 *@time 2021/7/7  23:50
 **/
class RecyclerRefreshLayout : RefreshLayout {

    private var recyclerView: RecyclerView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (recyclerView == null) {
            findView(this)
        }
    }

    override fun isReachBottom(): Boolean {
        return if (recyclerView == null) {

            false
        } else {

            val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
            val position = layoutManager.findLastVisibleItemPosition()
            val count = layoutManager.itemCount
            position > count - 2
        }
    }

    override fun showLoadView(isShow: Boolean) {
        recyclerView?.adapter?.let {
            if (it is LoadRecyclerAdapter) {
                it.showFootView(isShow)
            }
        }
    }

    private fun findView(view: View) {
        if (view is ViewGroup) {
            for (i in 0.until(view.childCount)) {

                val child = getChildAt(i)
                if (child is RecyclerView) {

                    recyclerView = child
                    if (recyclerView?.adapter!! is LoadRecyclerAdapter) {

                        (child.adapter as LoadRecyclerAdapter).setFootView(footView)
                    }

                    recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrollStateChanged(
                            recyclerView: RecyclerView,
                            newState: Int
                        ) {
                            super.onScrollStateChanged(recyclerView, newState)
                        }

                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            if (isAutoLoad) {

                                loadData()
                            }
                        }
                    })
                    break
                } else {

                    findView(child)
                }
            }
        }
    }

}