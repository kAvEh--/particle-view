package com.kaveh.particleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kaveh.particle.ParticleFactory

class MainActivity : AppCompatActivity() {
    val factory = ParticleFactory()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}