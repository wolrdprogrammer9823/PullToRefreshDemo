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

    private var countItem = 0
    private var lastItem = 0

    private var mIsScrolled = false

    var mIsAllScreen = false

    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

        super.onScrollStateChanged(recyclerView, newState)

        when (newState) {

            RecyclerView.SCROLL_STATE_IDLE -> {
                //停止状态

            }

            RecyclerView.SCROLL_STATE_DRAGGING -> {
                //拖拽

            }

            RecyclerView.SCROLL_STATE_SETTLING -> {
                //固定

            }

            else -> {}
        }

        if (newState == RecyclerView.SCROLL_STATE_DRAGGING
                     || newState == RecyclerView.SCROLL_STATE_SETTLING) {

            mIsScrolled = true
            mIsAllScreen = true
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        super.onScrolled(recyclerView, dx, dy)
        if (recyclerView.layoutManager is LinearLayoutManager) {

            mLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            countItem = mLayoutManager.itemCount
            lastItem = (mLayoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
        }

        if (mIsScrolled && countItem != lastItem && lastItem == countItem - 1) {

            onLoading(countItem, lastItem)
        }
    }

    abstract fun onLoading(countItem: Int, lastItem: Int)
}