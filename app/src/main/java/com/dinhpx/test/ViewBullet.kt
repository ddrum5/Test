package com.dinhpx.test

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class ViewBullet : View {

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(attrs)
    }

    private var length = 6

    private val paint = Paint()

    private fun initView(attrs: AttributeSet?) {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.ViewBullet)
            this.length = ta.getInt(R.styleable.ViewBullet_vb_length, 6)
            ta.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        for (i in 0 until length) {
            val radius = 20f
            paint.color = Color.BLUE
            canvas!!.drawCircle((60f * i) + radius + 5, radius + 5, radius, paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = (60) * length - 15
        val h = 45
        setMeasuredDimension(w, h)
    }

}

class MyView : View {

    private val path = Path()
    private val paint = Paint()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    // Get x and y and follow user motion events
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val pointX = event.x
        val pointY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN ->                 // Starts a new line in the path
                path.moveTo(pointX, pointY)
            MotionEvent.ACTION_MOVE ->                 // Draws line between last point and this point
                path.lineTo(pointX, pointY)
            else -> return false
        }
        invalidate()
        return true
    }

    private fun PaintSettings() {
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLUE
        paint.strokeWidth = 10f
    }

    //Constructor
    init {
        isFocusable = true
        isFocusableInTouchMode = true
        PaintSettings()
    }
}

