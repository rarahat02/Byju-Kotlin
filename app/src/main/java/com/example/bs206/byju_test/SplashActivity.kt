package com.example.bs206.byju_test

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity()
{

    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        prefManager = PrefManager(this)

        if (!prefManager.isFirstTimeLaunch)
        {
            val intent = Intent(this,CourseListActivity::class.java)
            startActivity(intent)
            finish()
        }
        else
        {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
            finish()

        }

    }
}
