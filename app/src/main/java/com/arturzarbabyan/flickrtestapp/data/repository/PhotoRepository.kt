package com.arturzarbabyan.flickrtestapp.data.repository

import com.arturzarbabyan.flickrtestapp.data.local.PhotoDao
import com.arturzarbabyan.flickrtestapp.data.remote.FlickrApi
import com.arturzarbabyan.flickrtestapp.domain.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val api: FlickrApi,
    private val photoDao: PhotoDao
) {
    suspend fun getPhotos(forceRefresh: Boolean): List<Photo> = withContext(Dispatchers.IO) { // ✅ Ensure this runs in background thread
        if (forceRefresh || photoDao.getAllPhotos().isEmpty()) {
            val response = api.getRecentPhotos()
            val photos = response.photos.photo.take(20)
            photoDao.deleteAll()
            photoDao.insertAll(photos)
            return@withContext photos
        } else {
            return@withContext photoDao.getAllPhotos().take(20)
        }
    }
}