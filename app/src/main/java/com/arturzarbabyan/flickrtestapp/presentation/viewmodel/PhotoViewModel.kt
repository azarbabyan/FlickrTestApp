package com.arturzarbabyan.flickrtestapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturzarbabyan.flickrtestapp.domain.model.Photo
import com.arturzarbabyan.flickrtestapp.domain.usecase.GetRecentPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val getRecentPhotosUseCase: GetRecentPhotosUseCase
) : ViewModel() {
    val photos = MutableLiveData<List<Photo>>()

    fun loadPhotos(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            photos.postValue(getRecentPhotosUseCase.execute(forceRefresh))
        }
    }


    fun restorePhotos(savedPhotos: List<Photo>) {
        photos.value = savedPhotos
    }
}