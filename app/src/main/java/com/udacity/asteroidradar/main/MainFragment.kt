package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.RvAsteroidsAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding?.lifecycleOwner = this

        binding?.viewModel = viewModel

        setHasOptionsMenu(true)

        viewModel.getNeoFeed()
        viewModel.getImageOfTheDay()

        viewModel.asteroidList.observe(viewLifecycleOwner) {
            Log.e("astreoid",it.toString())
            setupRV(it)
        }
        viewModel.imageOfTheDay.observe(viewLifecycleOwner) {
            Picasso.with(context).load(it.url).into(binding?.activityMainImageOfTheDay)
            binding?.activityMainImageOfTheDay?.contentDescription = it.title
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun setupRV(data : ArrayList<Asteroid>){
        binding?.asteroidRecycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // adapter
        val adapter = RvAsteroidsAdapter(data)
        binding?.asteroidRecycler?.adapter = adapter
    }
}
