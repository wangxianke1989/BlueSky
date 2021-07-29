package com.bluesky.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.bluesky.android.BlueSkyApplication
import com.bluesky.android.logic.model.Place
import com.google.gson.Gson

object PlaceDao {
    fun savePlace(place:Place){
        sharedPreferences().edit{
            putString("place",Gson().toJson(place))
        }
    }

    fun getSavedPlace():Place{
        val placeJson = sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = BlueSkyApplication.context.getSharedPreferences("sunny_weather",
        Context.MODE_PRIVATE)
}