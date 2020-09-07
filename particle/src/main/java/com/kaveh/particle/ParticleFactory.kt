package com.kaveh.particle

import kotlin.random.Random

class ParticleFactory {

    internal var particles: MutableList<Particle> = mutableListOf()
    internal var minVelocity = Pair(-10, -10)
    internal var maxVelocity = Pair(10, 10)
    internal var ttlRange = Pair(400, 700)
    internal var isChaos = false
    internal var maxParticles = 0
        set(value) {
            if (value in 1..5000) {
                field = value
                particles.clear()
                for (i in 0 until maxParticles) {
                    val tmp = Particle()
                    //TODO set initial particle parameters
                    initParticle(tmp)
                    particles.add(tmp)
                }
            }
        }
    internal var refreshRate = 100L
        set(value) {
            if (value in 200..1000) {
                field = value
            }
        }

    internal var currentPosition = Pair(100, 100)
        set(value) {
            if (value.first >= 0 && value.second >= 0)
                field = value
        }

    fun refreshStat() {
        val currentTime = System.currentTimeMillis()
        for (p in particles.size - 1 downTo 0) {
            if (currentTime > particles[p].creationTime + particles[p].ttl) {
                particles.removeAt(p)
                continue
            }
            particles[p].opacity =
                1 - (currentTime - particles[p].creationTime) / particles[p].ttl.toFloat()
            if (isChaos)
                particles[p].position = Pair(
                    particles[p].position.first + particles[p].velocity.first,
                    particles[p].position.second + particles[p].velocity.second
                )
            else
                particles[p].position = Pair(
                    particles[p].position.first +
                            (particles[p].velocity.first *
                                    (particles[p].velocity.second / (maxVelocity.second - minVelocity.second).toFloat())).toInt(),
                    particles[p].position.second + particles[p].velocity.second
                )
        }
        for (i in 0 until maxParticles - particles.size) {
            val tmp = Particle()
            initParticle(tmp)
            particles.add(tmp)
        }
    }

    private fun initParticle(p: Particle) {
        p.position = currentPosition
        p.velocity = Pair(
            Random.nextInt(minVelocity.first, maxVelocity.first),
            Random.nextInt(minVelocity.second, maxVelocity.second)
        )
        p.ttl = Random.nextInt(ttlRange.first, ttlRange.second)
    }
}