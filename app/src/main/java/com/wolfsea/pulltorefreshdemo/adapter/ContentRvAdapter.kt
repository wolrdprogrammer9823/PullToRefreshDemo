package com.wolfsea.pulltorefreshdemo.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wolfsea.pulltorefreshdemo.R
import com.wolfsea.pulltorefreshdemo.listener.OnLoadMoreListener
import com.wolfsea.pulltorefreshdemo.viewholder.ContentViewHolder
import com.wolfsea.pulltorefreshdemo.viewholder.FooterViewHolder

/**
 *@desc  Rv适配器
 *@author liuliheng
 *@time 2021/6/24  1:10
 **/
class ContentRvAdapter(
    context: Context,
    dataSource: MutableList<Int>,
    loadMoreListener: OnLoadMoreListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mContext = context
    private val mDataSource = dataSource
    private val mLoadMoreListener = loadMoreListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_FOOTER) {

            val view = LayoutInflater.from(mContext).inflate(R.layout.layout_footer, parent, false)
            FooterViewHolder(view)
        } else {

            val view = LayoutInflater.from(mContext).inflate(R.layout.layout_rv_item, parent, false)
            ContentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_CONTENT) {
            val mHolder = holder as ContentViewHolder
            mHolder.setContent("第${mDataSource[position]}行")
        }
    }

    override fun getItemCount(): Int = if (mLoadMoreListener.mIsAllScreen) mDataSource.size + 1 else mDataSource.size

    override fun getItemViewType(position: Int): Int =
        if (position == mDataSource.size && mLoadMoreListener.mIsAllScreen) TYPE_FOOTER else TYPE_CONTENT

    companion object {
        const val TYPE_CONTENT = 0x01
        const val TYPE_FOOTER = 0x02
    }
}