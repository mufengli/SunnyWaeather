package com.sunnyweather.android

import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {

    companion object{
        lateinit var context: Context
        const val token="u45WqFW215VBO0N2"
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }

}
