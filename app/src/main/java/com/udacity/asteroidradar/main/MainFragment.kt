package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.RvAsteroidsAdapter
import com.udacity.asteroidradar.StateManagement
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

        viewModel.stateManagement.observe(viewLifecycleOwner) {
            when (it) {
                StateManagement.DONE ->{
                    binding?.statusLoadingWheel?.visibility = View.GONE
                    binding?.ivRefresh?.visibility = View.GONE
                }
                StateManagement.LOADING -> {
                    binding?.statusLoadingWheel?.visibility = View.VISIBLE
                    binding?.ivRefresh?.visibility = View.GONE
                }
                StateManagement.ERROR -> {
                    binding?.statusLoadingWheel?.visibility = View.GONE
                    binding?.ivRefresh?.visibility = View.VISIBLE
                    Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        }

        setupRV()

        viewModel.imageOfTheDay.observe(viewLifecycleOwner) {
            Picasso.with(context).load(it.url).into(binding?.activityMainImageOfTheDay)
            binding?.activityMainImageOfTheDay?.contentDescription = it.title
        }

        binding?.ivRefresh?.setOnClickListener {
            viewModel.getNeoFeed()
            viewModel.getImageOfTheDay()
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

    private fun setupRV() {
        binding?.asteroidRecycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // adapter
        val adapter = RvAsteroidsAdapter()
        binding?.asteroidRecycler?.adapter = adapter
    }
}
