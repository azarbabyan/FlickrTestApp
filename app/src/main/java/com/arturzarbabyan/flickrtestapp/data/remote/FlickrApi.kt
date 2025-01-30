package com.arturzarbabyan.flickrtestapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    @GET("services/rest/")
    suspend fun getRecentPhotos(
        @Query("method") method: String = "flickr.photos.getRecent",
        @Query("api_key") apiKey: String = "da9d38d3dee82ec8dda8bb0763bf5d9c",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1
    ): FlickrResponse
}