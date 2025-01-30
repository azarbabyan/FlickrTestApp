package com.arturzarbabyan.flickrtestapp.presentation.ui

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arturzarbabyan.flickrtestapp.R
import com.arturzarbabyan.flickrtestapp.domain.model.Photo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class PhotoAdapter(private val onClick: (Photo, ImageView) -> Unit
) : ListAdapter<Photo, PhotoAdapter.PhotoViewHolder>(PhotoDiffCallback()) {

    class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgThumbnail: ImageView = view.findViewById(R.id.imgThumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        val layoutParams = view.layoutParams

        val screenWidth = parent.context.resources.displayMetrics.widthPixels
        val spanCount =
            if (parent.context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4

        val margin =
            if (spanCount == 2) (screenWidth * 0.1).toInt() else (screenWidth * 0.04).toInt()
        val spacing = margin / spanCount
        val imageSize = (screenWidth - (margin * 2) - ((spanCount - 1) * spacing)) / spanCount

        layoutParams.width = imageSize
        layoutParams.height = imageSize
        view.layoutParams = layoutParams
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = getItem(position)
        Glide.with(holder.imgThumbnail.context)
            .load(photo.getImageUrl())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(holder.imgThumbnail)

        holder.itemView.setOnClickListener {
            it.animate().scaleX(1.1f).scaleY(1.1f).setDuration(150).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(150)
            }
            onClick(photo, holder.imgThumbnail)
        }
    }

}