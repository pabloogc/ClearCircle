package com.minikorp.clearscore.app.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.minikorp.clearscore.R
import kotlin.math.min
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty

class CircularProgressView
@JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) :
    View(context, attrs, defStyle) {

    private val circleRect = RectF()
    private var radius: Float = 0f
    private var strokeWidth: Float = 40f
    @ColorInt
    private var startColor: Int = Color.RED
    @ColorInt
    private var endColor: Int = 0
    private var shader: Shader? = null
    private var circlePain = Paint(Paint.ANTI_ALIAS_FLAG)

    var progress by viewProperty(0)
    var maxProgress by viewProperty(100)

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressView, 0, 0)
        progress = a.getInt(R.styleable.CircularProgressView_startColor, 0)
        maxProgress = a.getInt(R.styleable.CircularProgressView_startColor, 100)
        startColor = a.getColor(R.styleable.CircularProgressView_startColor, Color.RED)
        endColor = a.getColor(R.styleable.CircularProgressView_endColor, Color.GREEN)
        strokeWidth = a.getDimension(R.styleable.CircularProgressView_strokeWidth, 20f)
        a.recycle()
        circlePain.strokeWidth = strokeWidth
        circlePain.style = Paint.Style.STROKE
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        //Biggest circle that fits inside view bounds
        val centerX = width / 2f
        val centerY = height / 2f
        radius = min(width / 2, height / 2) - strokeWidth / 2f
        circleRect.set(
            centerX - radius / 2, //left
            centerY - radius / 2, //top
            centerX + radius / 2, //right
            centerY + radius / 2 //bottom
        )
        if (changed || shader == null) {
            shader = SweepGradient(
                centerX,
                centerY,
                startColor,
                endColor
            )
            circlePain.shader = shader
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        //-90, android 0 is right, we want it to be at top
        canvas.rotate(-90f, circleRect.centerX(), circleRect.centerY())
        val sweep = (progress.toFloat() / maxProgress) * 360f
        canvas.drawArc(
            circleRect,
            0f, //start
            sweep, //sweep
            false, //use center
            circlePain
        )
        canvas.restore()
    }


    private fun <T> viewProperty(initialValue: T): ReadWriteProperty<Any?, T> {
        return Delegates.observable(initialValue) { _, oldValue, newValue ->
            if (oldValue != newValue) {
                invalidate()
            }
        }
    }
}