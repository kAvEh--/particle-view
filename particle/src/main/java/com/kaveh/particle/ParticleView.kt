package com.kaveh.particle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View

class ParticleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val factory = ParticleFactory()
    private val mPaintParticle = Paint()
    var particleColor = Color.parseColor("#000000")
        set(value) {
            field = value
            mPaintParticle.color = particleColor
        }

    fun setParticleOrigin(posX: Int, posY: Int) {
        factory.currentPosition = Pair(posX, posY)
    }

    fun setParticlesNum(num: Int) {
        factory.maxParticles = num
    }

    fun setVelocity(minX: Int, maxX: Int, minY: Int, maxY: Int) {
        //TODO validate input
        factory.minVelocity = Pair(minX, minY)
        factory.maxVelocity = Pair(maxX, maxY)
    }

    fun setLifeTime(minTTL: Int, maxTTL: Int) {
        //TODO validate input
        factory.ttlRange = Pair(minTTL, maxTTL)
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
        mPaintParticle.style = Paint.Style.FILL
        mPaintParticle.strokeCap = Paint.Cap.ROUND
        mPaintParticle.isAntiAlias = true
        mPaintParticle.color = particleColor
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        for (p in factory.particles) {
            mPaintParticle.alpha = (p.opacity * 255).toInt()
            canvas.drawCircle(
                p.position.first.toFloat(),
                p.position.second.toFloat(),
                10f, mPaintParticle
            )
        }
    }
}