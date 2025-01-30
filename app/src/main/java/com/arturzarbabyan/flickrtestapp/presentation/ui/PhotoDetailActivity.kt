package com.arturzarbabyan.flickrtestapp.presentation.ui

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.appcompat.app.AppCompatActivity
import com.arturzarbabyan.flickrtestapp.R
import com.arturzarbabyan.flickrtestapp.databinding.ActivityPhotoDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class PhotoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(android.R.transition.move)
        val photoUrl = intent.getStringExtra("photoUrl")
        Glide.with(this)
            .load(photoUrl)
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imgFullSize)
    }
}