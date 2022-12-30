package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.RvItemAsteroidBinding

class RvAsteroidsAdapter(private val items: ArrayList<Asteroid>) : RecyclerView.Adapter<RvAsteroidsAdapter.ViewHolder>() {

    class ViewHolder(binding: RvItemAsteroidBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvItemAsteroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]

        holder.apply {
            tvName.text = item.codename
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}
