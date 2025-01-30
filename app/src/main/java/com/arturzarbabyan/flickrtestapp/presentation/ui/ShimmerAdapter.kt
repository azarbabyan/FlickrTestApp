package com.arturzarbabyan.flickrtestapp.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arturzarbabyan.flickrtestapp.R

class ShimmerAdapter(private val itemCount: Int) :
    RecyclerView.Adapter<ShimmerAdapter.ShimmerViewHolder>() {

    class ShimmerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shimmer_item, parent, false)
        return ShimmerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShimmerViewHolder, position: Int) {
        // Nothing to bind, just a placeholder
    }

    override fun getItemCount(): Int = itemCount
}