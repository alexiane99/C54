package com.example.annexe6

import android.content.Intent
import android.content.Intent.ACTION_OPEN_DOCUMENT
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var boutonImage : Button
    lateinit var image : ImageView

    var lanceur: ActivityResultLauncher<Intent>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lanceur = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), CallbackImage())

        boutonImage = findViewById(R.id.boutonImage)
        image = findViewById(R.id.imageView)

        boutonImage.setOnClickListener {

            val intent = Intent(ACTION_OPEN_DOCUMENT)
            intent.setType("image/*");
            lanceur?.launch( intent )

            // lanceur.launch(Intent(this@MainActivity, ImageActivity::class.java))}

        }
    }


    inner class CallbackImage : ActivityResultCallback<ActivityResult> {
        override fun onActivityResult(result: ActivityResult) {
//            if(result.resultCode == RESULT_OK) {

//                val result = result.data
//
//                val getImg = result?.getStringExtra("lienImage")

                var intent = result.data
                var uri = intent!!.data
                image.setImageURI(uri)


        }
    }
}