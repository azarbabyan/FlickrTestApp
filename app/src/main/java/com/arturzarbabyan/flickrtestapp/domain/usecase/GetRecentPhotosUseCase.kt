package com.arturzarbabyan.flickrtestapp.domain.usecase

import com.arturzarbabyan.flickrtestapp.data.repository.PhotoRepository
import com.arturzarbabyan.flickrtestapp.domain.model.Photo
import javax.inject.Inject

class GetRecentPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend fun execute(forceRefresh: Boolean = false): List<Photo> {
        return repository.getPhotos(forceRefresh)
    }
}