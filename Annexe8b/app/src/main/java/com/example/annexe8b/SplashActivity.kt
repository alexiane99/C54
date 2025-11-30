package com.example.annexe8b

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.io.path.Path
import kotlin.io.path.moveTo

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

        var anim1 = ObjectAnimator.ofFloat(soleil, View.TRANSLATION_Y, 1000f)
        anim1.duration = 3000
        anim1.interpolator = BounceInterpolator()

        var anim2 = ObjectAnimator.ofFloat(soleil, View.SCALE_X, 10f)
        anim2.duration = 2000

        var anim3 = ObjectAnimator.ofFloat(soleil, View.SCALE_Y, 10f)
        anim3.duration = 2000

//        var set = AnimatorSet()
//        set.playTogether(anim1, anim2, anim3) // animations de même durée et jouées en même temps!

        button.setOnClickListener {

//            set.start()

            val path = Path()

            path.moveTo(soleil.x, soleil.y)
            path.lineTo(soleil.x, 1000f)

            val animPath = ObjectAnimator.ofFloat(soleil, View.X, View.Y, path)
            animPath.duration = 2000
            animPath.interpolator = BounceInterpolator()
            val scaleX = ObjectAnimator.ofFloat(soleil, View.SCALE_X, 25f)
            val scaleY = ObjectAnimator.ofFloat(soleil, View.SCALE_Y, 25f)
            scaleX.duration = 2000
            scaleY.duration = 2000

            val set = AnimatorSet()
            set.playTogether(animPath, anim2, anim3)
            set.start()

        }

//        animatorSet
//        path (x ,y )
//        val p : Path = Path()
//        p.moveTo
//        p.lineTo(400f, 1000f)
    }
}