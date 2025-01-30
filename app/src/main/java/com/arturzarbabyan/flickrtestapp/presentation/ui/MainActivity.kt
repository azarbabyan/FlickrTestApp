package com.arturzarbabyan.flickrtestapp.presentation.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.arturzarbabyan.flickrtestapp.databinding.ActivityMainBinding
import com.arturzarbabyan.flickrtestapp.domain.model.Photo
import com.arturzarbabyan.flickrtestapp.presentation.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PhotoViewModel by viewModels()
    private lateinit var adapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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

       setupRecyclerView()


        if (savedInstanceState != null) {
            val savedPhotos = savedInstanceState.getParcelableArrayList<Photo>("photos")
            if (savedPhotos != null) {
                viewModel.restorePhotos(savedPhotos)
            }
        } else {
            viewModel.loadPhotos()
        }

        viewModel.photos.observe(this) { photos ->
            adapter = PhotoAdapter(photos) { photo ->
                val intent = Intent(this, PhotoDetailActivity::class.java)
                intent.putExtra("photoUrl", photo.getImageUrl())
                startActivity(intent)
            }
            binding.recyclerView.adapter = adapter
        }

        binding.btnRefresh.setOnClickListener {
            viewModel.loadPhotos(forceRefresh = true)
        }


    }

    private fun setupRecyclerView() {
        val spanCount: Int
        val margin: Int
        val screenWidth = resources.displayMetrics.widthPixels

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            spanCount = 2
            margin = (screenWidth * 0.1).toInt()
        } else {
            spanCount = 4
            margin = (screenWidth * 0.04).toInt()
        }

        val spacing = margin / spanCount

        binding.recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        binding.recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, true))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("photos", ArrayList(viewModel.photos.value ?: emptyList()))
    }
}