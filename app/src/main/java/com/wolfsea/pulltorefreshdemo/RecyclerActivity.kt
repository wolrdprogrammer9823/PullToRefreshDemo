package com.wolfsea.pulltorefreshdemo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.wolfsea.pulltorefreshdemo.databinding.ViewListItemBinding
import com.wolfsea.pulltorefreshdemo.loadrefreshlayout.LoadRecyclerAdapter
import com.wolfsea.pulltorefreshdemo.loadrefreshlayout.base.OnLoadListener
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.android.synthetic.main.view_list_item.view.*
import kotlinx.coroutines.*

class RecyclerActivity : AppCompatActivity(),SwipeRefreshLayout.OnRefreshListener, OnLoadListener {

    private val dataSet = mutableListOf<String>()
    private lateinit var mAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        refreshLayout2.setOnRefreshListener(this)

        refreshLayout2.setOnLoadListener(this)

        mAdapter = RecyclerAdapter()
        recyclerView2.apply {
            layoutManager = LinearLayoutManager(this@RecyclerActivity)
            adapter = mAdapter
        }

        down()
    }


    override fun onRefresh() {
        refreshLayout2.setLoadEnable(true)
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                delay(3000)
                down()
                refreshLayout2.isRefreshing = false
            }
        }
    }

    override fun onLoad() {
       GlobalScope.launch {
           withContext(Dispatchers.Main){
               delay(3000)
               up()
               refreshLayout2.setLoading(false)
           }
       }
    }


    private fun up() {
        val index = dataSet.size
        for (i in index.until(index + 10)) {
            dataSet.add("测试数据:${i + 1}")
        }
        mAdapter.setDataSize(dataSet.size)
    }

    private fun down() {
        dataSet.clear()
        for (i in 0.until(10)) {
            val content = "测试数据:${i + 1}"
            dataSet.add(content)
        }
        mAdapter.setDataSize(dataSet.size)
    }

    private inner class RecyclerAdapter : LoadRecyclerAdapter() {

        private lateinit var mBinding: ViewListItemBinding

        override fun onCreateItemViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {

            mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this@RecyclerActivity),
                R.layout.view_list_item,
                parent,
                false
            )
            return LoadViewHolder(mBinding)
        }

        override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

            val value = dataSet[position]
            holder?.itemView?.tvTest?.text = value
            if (position % 2 == 0) {
                holder?.itemView?.tvTest?.setBackgroundColor(mBinding.tvTest.resources.getColor(R.color.teal_200))
            } else {
                holder?.itemView?.tvTest?.setBackgroundColor(mBinding.tvTest.resources.getColor(R.color.teal_700))
            }
        }
    }

    private inner class LoadViewHolder(mBiding: ViewListItemBinding) : RecyclerView.ViewHolder(mBiding.root)
}