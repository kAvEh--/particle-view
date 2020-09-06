package com.kaveh.particle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

class ParticleProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val factory = ParticleFactory()
    private val mPaintParticle = Paint()

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
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        for (p in factory.particles) {
            mPaintParticle.color = Color.argb((p.opacity * 255).toInt(), 200, 100, 150)
            canvas.drawCircle(
                p.position.first.toFloat(),
                p.position.second.toFloat(),
                25f, mPaintParticle
            )
        }
    }
}