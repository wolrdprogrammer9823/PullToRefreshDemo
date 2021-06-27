package com.wolfsea.pulltorefreshdemo.viewholder
import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.wolfsea.pulltorefreshdemo.R

/**
 *@desc  Rv底部ViewHolder
 *@author liuliheng
 *@time 2021/6/24  0:06
 **/
class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val contentLoadingProgressBar =
         itemView.findViewById<ContentLoadingProgressBar>(R.id.content_loading_progressbar)
}