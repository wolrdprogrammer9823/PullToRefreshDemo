package com.wolfsea.pulltorefreshdemo.viewholder
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.wolfsea.pulltorefreshdemo.R

/**
 *@desc  Rv内容ViewHolder
 *@author liuliheng
 *@time 2021/6/23  23:59
 **/
class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val contentTextView = itemView.findViewById<AppCompatTextView>(R.id.content_text)

    fun setContent(content: String?) {
        contentTextView.text = content
    }
}