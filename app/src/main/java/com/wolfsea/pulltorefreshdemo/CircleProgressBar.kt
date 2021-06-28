package com.wolfsea.pulltorefreshdemo
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 *@desc  圆形加载进度条
 *@author liuliheng
 *@time 2021/6/28  1:11
 **/
class CircleProgressBar(context: Context, attributeSet: AttributeSet)
    : View(context, attributeSet) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }

    companion object {
        val START_DEGREE = intArrayOf(45, 90, 135, 180, 225, 270, 315, 360)
        val END_DEGREE = intArrayOf(360, 45, 90, 135, 180, 225, 270, 315)



    }
}