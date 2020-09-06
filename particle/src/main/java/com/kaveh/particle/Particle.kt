package com.kaveh.particle

class Particle {

    private lateinit var mPosition: Pair<Int, Int>
    private lateinit var mVelocity: Pair<Int, Int>
    var ttl = 100
        set(value) {
            if (value in 100..1000) {
                field = value
            }
        }
    val creationTime: Long = System.currentTimeMillis()
    private var mOpacity = 1F
    private var duration: Int = 0

    init {
    }

    /**
     * set position.
     *
     */
    var position: Pair<Int, Int>
        get() = mPosition
        set(position) {
            //TODO Validate Position
            mPosition = position
        }

    /**
     * set velocity of particle.
     *
     */
    var velocity: Pair<Int, Int>
        get() = mVelocity
        set(velocity) {
            //TODO Validate Velocity
            mVelocity = velocity
        }

    /**
     * set opacity of particle.
     *
     */
    var opacity: Float
        get() = mOpacity
        set(opacity) {
            if (opacity in 0.0..1.0)
                mOpacity = opacity
        }
}