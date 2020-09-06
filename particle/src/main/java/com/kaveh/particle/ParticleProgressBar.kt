package com.kaveh.particle

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import kotlin.math.abs

class ParticleProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val factory = ParticleFactory()
    private val mPaintParticle = Paint()
    private val mPaintProgress = Paint()
    private var sideMargin = 100
    private var mIndicatorRadius = 26F
    private var isTouched = false
    private var mProgressColor = Color.parseColor("#6200EE")

    /**
     * @param progress:Float Should be 0 ~ 1.
     */
    var progress = 0f
        set(value) {
            if (value != field && (value >= 0 || value <= 1)) {
                field = value
                factory.currentPosition =
                    Pair(((width - 2 * sideMargin) * progress + sideMargin).toInt(), height / 2)
                invalidate()
            }
        }

    init {
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                factory.refreshStat()
                mainHandler.postDelayed(this, factory.refreshRate)
                invalidate()
            }
        })
        println(this.willNotDraw())
        mPaintParticle.style = Paint.Style.FILL
        mPaintParticle.strokeCap = Paint.Cap.ROUND
        mPaintParticle.isAntiAlias = true

        mPaintProgress.style = Paint.Style.STROKE
        mPaintProgress.strokeWidth = 15.0F
        mPaintProgress.strokeCap = Paint.Cap.ROUND
        mPaintProgress.isAntiAlias = true
        mPaintProgress.color = mProgressColor
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mIndicatorRadius = height * .5F

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        for (p in factory.particles) {
            mPaintParticle.color = Color.argb((p.opacity * 255).toInt(), 200, 100, 150)
            canvas.drawCircle(
                p.position.first.toFloat(),
                p.position.second.toFloat(),
                10f, mPaintParticle
            )
        }
        canvas.drawLine(
            factory.currentPosition.first.toFloat(),
            factory.currentPosition.second.toFloat(),
            (width - sideMargin).toFloat(),
            factory.currentPosition.second.toFloat(),
            mPaintProgress
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isTouchedNear(event.x)
                if (!isTouched) {
                    moveWithAnimation(
                        when {
                            event.x < mIndicatorRadius -> 0F
                            event.x > width - mIndicatorRadius -> 1F
                            else -> (event.x - mIndicatorRadius) / (width - 2 * mIndicatorRadius)
                        }
                    )
                }
                return true
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                if (isTouched) {
                    progress = when {
                        event.x < mIndicatorRadius -> 0F
                        event.x > width - mIndicatorRadius -> 1F
                        else -> (event.x - mIndicatorRadius) / (width - 2 * mIndicatorRadius)
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                isTouched = false
            }
            MotionEvent.ACTION_POINTER_UP -> {
            }
            else -> {
            }
        }
        return super.onTouchEvent(event)
    }

    private fun isTouchedNear(x: Float) {
        if (abs(factory.currentPosition.first - x) < mIndicatorRadius + 5) {
            isTouched = true
        }
    }

    private fun moveWithAnimation(newProgress: Float) {
        val tmp = progress
        val moveAnim = ObjectAnimator.ofFloat(
            this, "progress", tmp, newProgress
        )
        moveAnim.repeatCount = 0
        moveAnim.duration = 80
        moveAnim.interpolator = AccelerateInterpolator()
        moveAnim.start()
    }
}