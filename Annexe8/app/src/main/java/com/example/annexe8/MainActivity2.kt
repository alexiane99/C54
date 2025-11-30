package com.example.annexe8

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.View.TRANSLATION_Y
import android.view.animation.BounceInterpolator
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {

    lateinit var barre : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var init = true

        barre = findViewById(R.id.barre)

        var anim = ObjectAnimator.ofFloat(barre, View.Y, 150f)
        anim.duration = 2000

//        var anim2 = ObjectAnimator.ofFloat(barre, TRANSLATION_Y, 0f)
//        anim2.reverse()


        barre.setOnClickListener {

            if (init) {

                anim.start()
                init = false
            }
            else {
                anim.reverse()
                init = true
            }


        }
    }
}