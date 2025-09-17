package com.example.annexe3d

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //KOTLIN :
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        Log.i("cycle", "onCreate")
        println("onCreate")
    }

    //KOTLIN :
    override fun onStart() {
        super.onStart()

        Log.i("cycle", "onStart")
        println("onStart")
    }

    //KOTLIN :
    override fun onResume() {
        super.onResume()

        Log.i("cycle", "onResume")
        println("onResume")
    }

    //KOTLIN :
    override fun onRestart() {
        super.onRestart()

        Log.i("cycle", "onRestart")
        println("onRestart")
    }

    //KOTLIN :
    override fun onPause() {
        super.onPause()

        Log.i("cycle", "onPause")
        println("onPause")
    }

    //KOTLIN :
    override fun onStop() {
        super.onStop()

        Log.i("cycle", "onStop")
        println("onStop")
    }

    //KOTLIN :
    override fun onDestroy() {
        super.onDestroy()

        Log.i("cycle", "onDestroy")
        println("onDestroy")
    }
}