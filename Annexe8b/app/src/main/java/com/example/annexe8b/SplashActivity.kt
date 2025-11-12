package com.example.annexe8b

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {

    lateinit var button : Button
    lateinit var soleil : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        button = findViewById(R.id.button)
        soleil = findViewById(R.id.soleil)

//        var anim1 = ObjectAnimator.ofFloat(textview, View.SCALE_X, 3f)
//        var anim2 = ObjectAnimator.ofFloat(textview, View.SCALE_Y, 3f)
        var anim1 = ObjectAnimator.ofFloat(soleil, View.TRANSLATION_Y, 10f)
        anim1.duration = 2000
        anim1.interpolator = BounceInterpolator()
        var anim2 = ObjectAnimator.ofFloat(soleil, View.SCALE_X, 3f)
        var anim3 = ObjectAnimator.ofFloat(soleil, View.SCALE_Y, 3f)

        var set = AnimatorSet()
        set.duration = 300
        set.playSequentially(anim1, anim2, anim3)

        button.setOnClickListener {

            set.start()
        }

//        animatorSet
//        path (x ,y )
//        val p : Path = Path()
//        p.moveTo
//        p.lineTo(400f, 1000f)
    }
}