package com.kaveh.particle

import android.os.Handler
import android.os.Looper

class ParticleFactory {

    val particles: MutableList<Particle> = mutableListOf()
    var ttl = 1000
        set(value) {
            if (value in 10..1000) {
                field = value
            }
        }
    var maxParticles = 0
        set(value) {
            if (value in 1..100) {
                field = value
                particles.clear()
                for (i in 0 until maxParticles) {
                    val tmp = Particle()
                    //TODO set initial particle parameters
                    tmp.position = currentPosition
                    tmp.velocity = Pair(1F,1F)
                    particles.add(tmp)
                }
            }
        }
    var refreshRate = 100L
        set(value) {
            if (value in 200..1000) {
                field = value
            }
        }

    var currentPosition = Pair(100,100)
        set(value) {
            if (value.first >= 0 && value.second >= 0)
                field = value
        }

    fun refreshStat() {
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
            //TODO set particle parameters
            tmp.position = currentPosition
            tmp.velocity = Pair(1F,1F)
            particles.add(tmp)
        }
    }
}