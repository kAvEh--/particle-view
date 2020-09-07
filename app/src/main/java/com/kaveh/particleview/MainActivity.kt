package com.kaveh.particleview

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        particlePB.setParticlesNum(35)
        particlePB.setVelocity(-20, 0, -10, 10)
        particlePB.setLifeTime(300, 800)

        particleView.setParticlesNum(835)
        particleView.setLifeTime(300, 800)
        particleView.particleColor = Color.parseColor("#d00000")

        particleView2.setParticlesNum(835)
        particleView2.setLifeTime(300, 800)
        particleView2.particleColor = Color.parseColor("#a11d33")

        particleView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            particleView.setParticleOrigin(particleView.width / 5, particleView.height)
            particleView.setVelocity(-20, 20, particleView.height / -8, 0)

            particleView2.setParticleOrigin(
                (particleView.width / 1.25).toInt(),
                particleView.height
            )
            particleView2.setVelocity(-25, 25, particleView.height / -15, 0)
        }
    }
}