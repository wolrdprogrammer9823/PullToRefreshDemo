package com.wolfsea.pulltorefreshdemo.listener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wolfsea.pulltorefreshdemo.logger.log

/**
 *@desc  加载更多监听器
 *@author liuliheng
 *@time 2021/6/24  0:56
 **/
abstract class OnLoadMoreListener : RecyclerView.OnScrollListener() {

    private var mLastVisibleItem = 0

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

        super.onScrollStateChanged(recyclerView, newState)
        when (newState) {

            RecyclerView.SCROLL_STATE_IDLE -> {
                //停止状态
                //log("SCROLL_STATE_IDLE")
                val condition = mLastVisibleItem == recyclerView.adapter?.itemCount!! - 1
                if (condition) {
                    onLoading()
                }
            }

            RecyclerView.SCROLL_STATE_DRAGGING -> {
                //拖拽
                //log("SCROLL_STATE_DRAGGING")
            }

            RecyclerView.SCROLL_STATE_SETTLING -> {
                //固定
                //log("SCROLL_STATE_SETTLING")
            }

            else -> {}
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is LinearLayoutManager) {
            mLastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
        }
    }

    abstract fun onLoading()
}