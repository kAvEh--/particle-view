package com.kaveh.particle

import android.os.Handler
import android.os.Looper
import kotlin.math.max

class ParticleFactory {

    private val particles: MutableList<Particle> = mutableListOf()
    var ttl = 3000
        set(value) {
            if (value in 10..1000) {
                field = value
            }
        }
    var maxParticles = 50
        set(value) {
            if (value in 10..100) {
                field = value
            }
        }
    var refreshRate = 2000L
        set(value) {
            if (value in 200..1000) {
                field = value
            }
        }


    init {
        for (i in 1..maxParticles) {
            val tmp = Particle()
            //TODO set initial particle parameters
            particles.add(tmp)
        }

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                refreshStat()
                mainHandler.postDelayed(this, refreshRate)
            }
        })
    }

    private fun refreshStat() {
        val currentTime = System.currentTimeMillis()
        for (p in particles.size - 1 downTo 0) {
            if (currentTime > particles[p].creationTime + ttl) {
                particles.removeAt(p)
                continue
            }
            particles[p].opacity = 1 - (currentTime - particles[p].creationTime) / ttl.toFloat()
        }
        for (i in 0 until maxParticles - particles.size) {
            val tmp = Particle()
            //TODO set initial particle parameters
            particles.add(tmp)
        }
    }
}