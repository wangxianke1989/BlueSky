package com.bluesky.android.ui.weather

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bluesky.android.logic.Repository

class WeatherViewModel: ViewModel() {

    private val locationLiveData = MutableLiveData<com.bluesky.android.logic.model.Location>()

    var locationLng=""
    var locationLat =""
    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData){
        location -> Repository.refreshWeather(location.lng,location.lat,placeName)
    }

    fun refreshWeather(lng:String,lat:String){
        locationLiveData.value = com.bluesky.android.logic.model.Location(lng,lat)
    }
}