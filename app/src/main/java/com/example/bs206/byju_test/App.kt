package com.example.bs206.byju_test

import android.app.Application
import android.os.Build
import android.os.StrictMode

/**
 * Created by bs206 on 2/7/18.
 */
class App : Application()
{
    override fun onCreate()
    {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            // Kitkat and lower has a bug that can cause incorrect strict mode
            // warnings about expected activity counts

            enableStrictMode()
        }
    }

    fun enableStrictMode()
    {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build())
    }
}