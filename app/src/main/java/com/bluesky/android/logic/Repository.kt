package com.bluesky.android.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bluesky.android.logic.model.Place
import com.bluesky.android.logic.network.BlueSkyNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

object Repository {
    fun searchPlaces(query:String) = liveData(Dispatchers.IO){
        var result = try {
            val placeResponse = BlueSkyNetwork.searchPlaces(query)
            if (placeResponse.status == "ok"){
                val places = placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch(e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}