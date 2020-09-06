package com.kaveh.particleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kaveh.particle.ParticleFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val factory = ParticleFactory()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        particlePB.factory.maxParticles = 35
        particlePB.setVelocity(-20,0,-10,10)
        particlePB.setLifeTime(300, 800)
    }
}