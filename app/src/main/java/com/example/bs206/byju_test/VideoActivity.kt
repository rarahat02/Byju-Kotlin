package com.example.bs206.byju_test

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_video.*
import android.net.Uri


class VideoActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val packageName = "com.example.bs206.byju_test"


        val path = "android.resource://" + packageName + "/" + R.raw.splash_video
        videoView.setVideoURI(Uri.parse(path))
        videoView.start()

        next_button.setOnClickListener {

            val intent =  Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }


    }
}
