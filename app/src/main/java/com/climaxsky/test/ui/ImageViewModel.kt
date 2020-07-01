package com.climaxsky.test.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.climaxsky.test.base.BaseViewModel
import com.climaxsky.test.data.ImageEntity
import com.climaxsky.test.utils.AssetJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImageViewModel: BaseViewModel() {

    private val repoImage = MutableLiveData<List<ImageEntity>>()

    val _reporepoImage: MutableLiveData<List<ImageEntity>>
        get() = repoImage

    fun getImage(context: Context, page: Int) {
        Log.d("TheCuong","page "+ page)
        val from: Int = 0 + (page * 20)
        val to: Int = 20 + (page * 20)

        val jsonFileString = AssetJson.getJsonDataFromAsset(context, "data.json")

        val listTemp: List<ImageEntity> =
            Gson().fromJson(jsonFileString, object : TypeToken<List<ImageEntity>>() {}.type)

        if (from < listTemp.size && to < listTemp.size) {
            _reporepoImage.postValue(listTemp.subList(from, to))
        }
    }
}