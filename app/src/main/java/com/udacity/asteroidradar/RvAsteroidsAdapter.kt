package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.databinding.RvItemAsteroidBinding

class RvAsteroidsAdapter : ListAdapter<Asteroid, RvAsteroidsAdapter.ViewHolder>(AsteroidsDiffCallback()) {

    class ViewHolder(binding: RvItemAsteroidBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvName
        val ivStatus = binding.ivStatus
        val tvDate = binding.tvDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvItemAsteroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)
        val context = holder.itemView.context

        holder.apply {
            tvName.text = item.codename
            tvDate.text = item.closeApproachDate
        }

        if (item.isPotentiallyHazardous) {
            Picasso.with(context).load(R.drawable.ic_status_potentially_hazardous).into(holder.ivStatus)
        } else {
            Picasso.with(context).load(R.drawable.ic_status_normal).into(holder.ivStatus)
        }
    }
}

class AsteroidsDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}