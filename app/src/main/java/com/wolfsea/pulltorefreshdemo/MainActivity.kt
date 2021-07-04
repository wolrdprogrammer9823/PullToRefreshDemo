package com.wolfsea.pulltorefreshdemo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.wolfsea.pulltorefreshdemo.adapter.ContentRvAdapter
import com.wolfsea.pulltorefreshdemo.extendmethod.toast
import com.wolfsea.pulltorefreshdemo.listener.OnLoadMoreListener
import com.wolfsea.pulltorefreshdemo.listener.OnRefreshingListener
import com.wolfsea.pulltorefreshdemo.listener.OnSmartLoadMoreListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var count = 0
    private var listData = mutableListOf<Int>()

    private var mRvAdapter: ContentRvAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    //加载更多监听器
    private val loadMoreListener: OnLoadMoreListener = object : OnLoadMoreListener() {
        override fun onLoading() {
            val notInRefreshing = !swipe_refresh_layout.isRefreshing
            if (notInRefreshing) {

                mRvAdapter?.mIsLoadMore = true
                mRvAdapter?.notifyDataSetChanged()

                GlobalScope.launch {
                    delay(3000)
                    withContext(Dispatchers.Main) {
                        getData(LOAD_MORE)
                    }
                }
            }
        }
    }

    /**
     *@desc 初始化数据
     *@author: liuliheng
     *@time: 2021/6/25 0:07
    **/
    private fun init() {

        getData(RESET)

        initRecyclerView()
        initSwipeRefreshLayout()
    }

    /**
     *@desc 初始化RecyclerView
     *@author:liuliheng
     *@time: 2021/6/25 0:27
    **/
    private fun initRecyclerView() {

        mRvAdapter = ContentRvAdapter(this@MainActivity, listData, loadMoreListener)

        recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = DefaultItemAnimator()
            adapter = mRvAdapter
            //addOnScrollListener(loadMoreListener)
        }
    }

    /**
     *@desc 初始化SwipeRefreshLayout
     *@author:liuliheng
     *@time: 2021/6/25 0:28
    **/
    private fun initSwipeRefreshLayout() {

        swipe_refresh_layout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_light
        )

        //swipe_refresh_layout.setProgressBackgroundColorSchemeResource(android.R.color.white)

        swipe_refresh_layout.setOnRefreshingListener(object : OnRefreshingListener{
            override fun onRefreshing() {
                GlobalScope.launch {
                    delay(3000)
                    withContext(Dispatchers.Main) {
                        getData(REFRESH)
                    }
                }
            }
        })

        swipe_refresh_layout.setOnLoadMoreListener(recyclerview,object : OnSmartLoadMoreListener {
            override fun onLoadMore() {
                GlobalScope.launch {
                    delay(3000)
                    withContext(Dispatchers.Main) {
                        getData(LOAD_MORE)
                    }
                }
            }
        })
    }

    /**
     *@desc 初始化数据
     *@author:liuliheng
     *@time: 2021/6/25 0:08
     **/
    private fun getData(type: String) {
        when (type) {
            RESET -> {
               //重置
               listData.clear()
               count = 0
                for (i in 0 until 3) {
                    count += 1
                    listData.add(count)
                }
            }
            REFRESH -> {
                //刷新
                count = listData.size

                for (i in 0 until 13) {

                    count += 1
                    listData.add(0, count)
                }

                mRvAdapter?.notifyDataSetChanged()
                if (swipe_refresh_layout.isRefreshing) {

                    swipe_refresh_layout.isRefreshing = false
                }

                toast("刷新完成")
            }
            LOAD_MORE -> {
                //加载更多
                count = 0
                for (i in 0 until 3) {
                    count += 1
                    listData.add(listData.size, count)
                }

                mRvAdapter?.mIsLoadMore = false
                mRvAdapter?.notifyDataSetChanged()

                if (swipe_refresh_layout.isRefreshing) {

                    swipe_refresh_layout.isRefreshing = false
                }

                toast("加载完成")
            }
            else -> {}
        }
    }

    companion object {
        const val RESET = "reset"
        const val REFRESH = "refresh"
        const val LOAD_MORE = "load_more"
    }
}