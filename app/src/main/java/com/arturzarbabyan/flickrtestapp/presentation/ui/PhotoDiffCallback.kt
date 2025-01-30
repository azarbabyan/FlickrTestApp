package com.arturzarbabyan.flickrtestapp.presentation.ui

import androidx.recyclerview.widget.DiffUtil
import com.arturzarbabyan.flickrtestapp.domain.model.Photo

class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}