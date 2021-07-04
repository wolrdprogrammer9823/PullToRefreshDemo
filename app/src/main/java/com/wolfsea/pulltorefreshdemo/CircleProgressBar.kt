package com.wolfsea.pulltorefreshdemo
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.wolfsea.pulltorefreshdemo.logger.log
import kotlinx.coroutines.*

/**
 *@desc  圆形加载进度条
 *@author liuliheng
 *@time 2021/6/28  1:11
 **/
class CircleProgressBar(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private lateinit var roundPaint: Paint

    private lateinit var ovalRectF: RectF

    private var index = 0

    private var circleWidth = 0
    private var circleHeight = 0

    private var minDpValue = 0F
    private var minWidthValue = 0F
    private var minHeightValue = 0F
    private var defaultDpValue = 0F

    init {
        initData(context)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.EXACTLY -> {
                circleWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
            }

            MeasureSpec.AT_MOST,MeasureSpec.UNSPECIFIED -> {
                circleWidth =
                    MeasureSpec.getSize(widthMeasureSpec)
                        .coerceAtMost(defaultDpValue.toInt()) - paddingLeft - paddingRight
            }

            else -> {}
        }

        when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.EXACTLY -> {
                circleHeight = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
            }

            MeasureSpec.AT_MOST,MeasureSpec.UNSPECIFIED ->{
                circleHeight =
                    MeasureSpec.getSize(heightMeasureSpec)
                        .coerceAtMost(defaultDpValue.toInt())  - paddingTop - paddingBottom
            }

            else -> {}
        }

        log("width:${circleWidth},height:${circleHeight}")
    }

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)
        canvas?.apply {

            ovalRectF.set(
                minDpValue,
                minDpValue,
                circleWidth.toFloat() - minDpValue,
                circleHeight.toFloat() - minDpValue
            )

            drawArc(
                ovalRectF,
                START_DEGREE[index.rem(START_DEGREE.size)],
                SWEEP_ANGLE,
                false,
                roundPaint
            )
        }
    }

    /**
     *@desc 初始化方法
     *@author:liuliheng
     *@time: 2021/6/28 23:11
    **/
    private fun initData(context: Context) {

        minDpValue = context.resources.getDimension(R.dimen.dp_5)
        minWidthValue = context.resources.getDimension(R.dimen.dp_30)
        minHeightValue = context.resources.getDimension(R.dimen.dp_30)
        defaultDpValue = context.resources.getDimension(R.dimen.dp_45)

        roundPaint = Paint()
        roundPaint.isAntiAlias = true
        roundPaint.style = Paint.Style.STROKE
        roundPaint.color = context.resources.getColor(R.color.teal_200)
        roundPaint.strokeWidth = minDpValue

        ovalRectF = RectF()

        startDraw()
    }

    /**
     *@desc 开始绘制
     *@author:liuliheng
     *@time: 2021/6/28 23:34
    **/
    private fun startDraw() {
        GlobalScope.launch {
            while (true) {
                delay(200)
                withContext(Dispatchers.Main) {
                    index++
                    invalidate()
                }
            }
        }
    }

    companion object {
        val START_DEGREE = floatArrayOf(45F, 90F, 135F, 180F, 225F, 270F, 315F, 360F)
        const val SWEEP_ANGLE = 315F
    }

}