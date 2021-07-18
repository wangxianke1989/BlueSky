package com.bluesky.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class BlueSkyApplication : Application() {
    companion object{
        const val TOKEN="dfcgS18cmJfRal06"
        @SuppressLint("StaticFieldLeak")
        lateinit var  context:Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}