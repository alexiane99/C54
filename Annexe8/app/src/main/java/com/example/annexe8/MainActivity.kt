package com.example.annexe8

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.BounceInterpolator
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var bouton : Button
    lateinit var view : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bouton = findViewById(R.id.button)
        view = findViewById(R.id.view)

        var anim = ObjectAnimator.ofFloat(view, View.X, 1070f ) // bouton va se déplacer de x = 500px à x = 1070px
        anim.duration = 2000
        anim.interpolator = BounceInterpolator()
        anim.repeatCount = ObjectAnimator.INFINITE
        anim.repeatMode = ObjectAnimator.REVERSE

        var anim2 = ObjectAnimator.ofFloat(view, View.ROTATION_X, 360f)
        anim2.duration = 1000
        anim2.interpolator = BounceInterpolator()
        anim2.repeatCount = ObjectAnimator.INFINITE
        anim2.repeatMode = ObjectAnimator.REVERSE

        var animatorSet = AnimatorSet()
        animatorSet.playTogether(anim, anim2)
        //animatorSet.playSequentially(anim, anim2)

        bouton.setOnClickListener{

            animatorSet.start()
//          anim.start() // démarrer l’animation
//          anim2.start()

        }


    }


}