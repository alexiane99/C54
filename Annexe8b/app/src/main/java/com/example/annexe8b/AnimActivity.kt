package com.example.annexe8b

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AnimActivity : AppCompatActivity() {

    lateinit var textview : TextView
    lateinit var boutonStart : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_anim)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textview = findViewById(R.id.text_view)
        boutonStart = findViewById(R.id.boutonStart)

        var anim1 = ObjectAnimator.ofFloat(textview, View.SCALE_X, 3f)
        var anim2 = ObjectAnimator.ofFloat(textview, View.SCALE_Y, 3f)
        var anim3 = ObjectAnimator.ofFloat(textview, View.ALPHA, 1f)

        var animatorSet = AnimatorSet()
        animatorSet.setDuration(2000)
        animatorSet.interpolator = BounceInterpolator()
        animatorSet.playTogether(anim1, anim2, anim3)

        boutonStart.setOnClickListener {
            animatorSet.start()
        }

    }
}