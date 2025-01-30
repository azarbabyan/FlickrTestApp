package com.arturzarbabyan.flickrtestapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey val id: String,
    val server: String,
    val secret: String
) : Parcelable {
    fun getImageUrl(): String {
        return "https://live.staticflickr.com/$server/${id}_${secret}_b.jpg"
    }
}