package com.wolfsea.pulltorefreshdemo.loadrefreshlayout.base
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.wolfsea.pulltorefreshdemo.R
import kotlin.math.abs

/**
 *@desc 自定义的SwipeRefreshLayout
 *@author liuliheng
 *@time 2021/7/9  21:39
 **/
abstract class RefreshLayout(context: Context, attributeSet: AttributeSet?) :
    SwipeRefreshLayout(context, attributeSet) {

    private var mYDown = 0
    private var mLastY = 0
    private var mPrevX = 0F

    private var mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop

    //设置滑动到底部自动加载更多
    protected var isAutoLoad = true
    protected var footView: View = LayoutInflater.from(context).inflate(R.layout.view_list_footer, null)

    private var isLoading = false
    private var isLoadEnable = false

    private var mOnLoadListener: OnLoadListener? = null
    
    /*
    * 解决SwipeRefreshLayout包含ViewPager时的滑动冲突
    * */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {

                mPrevX = MotionEvent.obtain(ev).x
            }

            MotionEvent.ACTION_MOVE -> {

                val eventX = ev.x
                val diffX = abs(eventX - mPrevX)
                if (diffX > mTouchSlop) {

                    return false
                }
            }
            else -> {}
        }

        return super.onInterceptTouchEvent(ev)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {

                mYDown = ev.rawY.toInt()
            }

            MotionEvent.ACTION_MOVE -> {

                mLastY = ev.rawY.toInt()
                loadData()
            }

            else -> {}
        }
        return super.dispatchTouchEvent(ev)
    }

    /*
    * 是否是上拉操作
    * */
    private fun isPullUp(): Boolean = mYDown > mLastY
    
    /*
    * 是否滚动到了底部
    * */
    abstract fun isReachBottom(): Boolean

    /*
    * 控制加载更多的View显示与隐藏     
    * */
    abstract fun showLoadView(isShow: Boolean)
    
    /*
    * 加载数据
    * */
    protected fun loadData() {
        val reachBottom = isReachBottom()
        val pullUp = isPullUp()
        if (reachBottom && !isLoading && pullUp && isLoadEnable) {
            setLoading(true)
            mOnLoadListener?.onLoad()
        }
    }

    /*
    * 设置当前状态为加载状态
    * */
    fun setLoading(loading: Boolean) {
        isLoading = loading
        if (isLoading) {

            showLoadView(true)
        } else {

            showLoadView(false)
            mLastY = 0
            mYDown = 0
        }
    }

    /*
    * 设置是否可以上拉加载更多
    * */
    fun setLoadEnable(enable: Boolean) {
        this.isLoadEnable = enable
    }
    
    fun setOnLoadListener(onLoadListener: OnLoadListener?) {
        setLoadEnable(true)
        mOnLoadListener = onLoadListener
    }
}