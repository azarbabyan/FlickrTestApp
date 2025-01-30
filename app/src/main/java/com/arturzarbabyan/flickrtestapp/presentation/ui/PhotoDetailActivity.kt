package com.arturzarbabyan.flickrtestapp.presentation.ui

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(getColor(android.R.color.transparent), getColor(android.R.color.transparent)),
            navigationBarStyle = SystemBarStyle.light(getColor(android.R.color.transparent), getColor(android.R.color.transparent))
        )
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.root.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }
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