package com.kaveh.particle

import android.os.Handler
import android.os.Looper
import kotlin.math.max

class ParticleFactory {

    private val particles: MutableList<Particle> = mutableListOf()
    var ttl = 300
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



}