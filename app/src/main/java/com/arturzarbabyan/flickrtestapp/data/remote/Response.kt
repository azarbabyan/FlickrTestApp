package com.arturzarbabyan.flickrtestapp.data.remote

import com.arturzarbabyan.flickrtestapp.domain.model.Photo
import kotlinx.serialization.Serializable

@Serializable
data class FlickrResponse(
    val photos: Photos
)

@Serializable
data class Photos(
    val photo: List<Photo>
)