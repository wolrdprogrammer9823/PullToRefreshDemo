package com.wolfsea.pulltorefreshdemo.loadrefreshlayout
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

/**
 *@desc  LoadRecyclerAdapter
 *@author liuliheng
 *@time 2021/7/7  23:07
 **/
abstract class LoadRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var footView: View? = null
    private var footerCount = 0
    private var dataSize = 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager
        layoutManager?.let {
            if (it is GridLayoutManager) {
                it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {

                        return if (getItemViewType(position) == 1) 1 else it.spanCount
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) createItemViewHolder(parent) else FooterViewHolder(footView!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is FooterViewHolder) {
            onBindItemViewHolder(holder, position)
        }
    }

    override fun getItemCount(): Int = dataSize + footerCount

    override fun getItemViewType(position: Int): Int {
        return if (position == dataSize) -1 else 1
    }

    abstract fun createItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    abstract fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    fun setFootView(footView: View) {

        this.footView = footView
        notifyDataSetChanged()
    }

    fun showFootView(show: Boolean) {

        footerCount = if (show) 1 else 0

        GlobalScope.launch {
            withContext(Dispatchers.Main) {

                notifyDataSetChanged()
            }
        }
    }

    fun setDataSize(dataSize: Int) {

        this.dataSize = dataSize
        GlobalScope.launch {
            withContext(Dispatchers.Main) {

                notifyDataSetChanged()
            }
        }
    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)
}