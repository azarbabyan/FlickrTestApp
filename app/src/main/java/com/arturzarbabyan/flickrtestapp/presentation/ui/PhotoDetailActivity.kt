package com.arturzarbabyan.flickrtestapp.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arturzarbabyan.flickrtestapp.R
import com.arturzarbabyan.flickrtestapp.databinding.ActivityPhotoDetailBinding
import com.bumptech.glide.Glide

class PhotoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoUrl = intent.getStringExtra("photoUrl")
        Glide.with(this)
            .load(photoUrl)
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .into(binding.imgFullSize)
    }
}