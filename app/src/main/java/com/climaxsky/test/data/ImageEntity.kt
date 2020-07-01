package com.climaxsky.test.data

import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel
data class ImageEntity(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("createdAt")
    val createdAt: String ? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("avatar")
    val avatar: String? = ""
)