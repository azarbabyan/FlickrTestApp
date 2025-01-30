package com.arturzarbabyan.flickrtestapp.presentation.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.arturzarbabyan.flickrtestapp.R
import com.arturzarbabyan.flickrtestapp.databinding.ActivityMainBinding
import com.arturzarbabyan.flickrtestapp.domain.model.Photo
import com.arturzarbabyan.flickrtestapp.presentation.viewmodel.PhotoViewModel
import com.arturzarbabyan.flickrtestapp.utils.NetworkUtils
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PhotoViewModel by viewModels()
    private lateinit var adapter: PhotoAdapter
    private lateinit var shimmerAdapter: ShimmerAdapter

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
        checkInternetAndShowSnackbar()
        setupRecyclerView()
        showShimmerEffect()

        if (savedInstanceState != null) {
            val savedPhotos = savedInstanceState.getParcelableArrayList<Photo>("photos")
            if (savedPhotos != null) {
                viewModel.restorePhotos(savedPhotos)
            }
        } else {
            viewModel.loadPhotos()
        }

        viewModel.photos.observe(this) { photos ->
            if (photos.isNotEmpty()){
                hideShimmerEffect()
                adapter.submitList(photos)
                binding.recyclerView.adapter = adapter
            }
            }


        binding.btnRefresh.setOnClickListener {
            if (!NetworkUtils.isInternetAvailable(this)) {
                Toast.makeText(this, "No Internet Connection. Showing cached photos.", Toast.LENGTH_SHORT).show()
            }else{
                showShimmerEffect()
                cleanUpOldCache()
                viewModel.loadPhotos(forceRefresh = true)
            }

        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            if (!NetworkUtils.isInternetAvailable(this)) {
                Toast.makeText(this, "No Internet Connection. Showing cached photos.", Toast.LENGTH_SHORT).show()
            }else{
                showShimmerEffect()
                cleanUpOldCache()
                viewModel.loadPhotos(forceRefresh = true)
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }


    }

    private fun checkInternetAndShowSnackbar() {
        if (!NetworkUtils.isInternetAvailable(this)) {
            Snackbar.make(binding.root, "No Internet. Showing cached data.", Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(this, R.color.error_red))
                .setTextColor(ContextCompat.getColor(this, R.color.white))
                .show()
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerViewContainer.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.shimmerViewContainer.startShimmer()
    }

    private fun hideShimmerEffect() {
        binding.shimmerViewContainer.stopShimmer()
        binding.shimmerViewContainer.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        adapter = PhotoAdapter { photo,imageView ->
            val intent = Intent(this, PhotoDetailActivity::class.java)
            intent.putExtra("photoUrl", photo.getImageUrl())
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                imageView,
                "photo_transition"
            )
            startActivity(intent)
        }
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
        val animator = DefaultItemAnimator()
        animator.addDuration = 300
        animator.removeDuration = 300
        binding.recyclerView.itemAnimator = animator
        binding.shimmerRecyclerView.layoutManager = GridLayoutManager(this, spanCount)
        binding.shimmerRecyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, true))
        shimmerAdapter = ShimmerAdapter(20)
        binding.shimmerRecyclerView.adapter = shimmerAdapter
    }

    private fun cleanUpOldCache() {
        lifecycleScope.launch(Dispatchers.IO) {
            Glide.get(applicationContext).clearDiskCache()
        }
        Glide.get(applicationContext).clearMemory()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("photos", ArrayList(viewModel.photos.value ?: emptyList()))
    }
}