package com.udacity.asteroidradar.scenes.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.utilities.StateManagement
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null

    private lateinit var viewModel: MainViewModel

    private val adapter = RvAsteroidsAdapter(RvAsteroidsAdapter.OnClickListener {
        findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding?.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val database = AsteroidDatabase.getInstance(application).asteroidDAO
        val viewModelFactory = MainViewModelFactory(database)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding?.viewModel = viewModel

        setHasOptionsMenu(true)

        binding?.asteroidRecycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.asteroidRecycler?.adapter = adapter

        viewModel.stateManagement.observe(viewLifecycleOwner) {
            when (it!!) {
                StateManagement.LOADING -> {
                    binding?.statusLoadingWheel?.visibility = View.VISIBLE
                    binding?.ivRefresh?.visibility = View.GONE
                }
                StateManagement.DONE -> {
                    binding?.statusLoadingWheel?.visibility = View.GONE
                    binding?.ivRefresh?.visibility = View.GONE
                }
                StateManagement.ERROR -> {
                    binding?.statusLoadingWheel?.visibility = View.GONE
                    binding?.ivRefresh?.visibility = View.VISIBLE
                    Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.imageOfTheDay.observe(viewLifecycleOwner) {
            Picasso.with(context).load(it.url).into(binding?.activityMainImageOfTheDay)
        }

        viewModel.asteroids?.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding?.ivRefresh?.setOnClickListener {
            viewModel.getNeoFeed()
            viewModel.getImageOfTheDay()
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null //to prevent memory leaks
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.view_week_asteroids -> {
                viewModel.getWeekAsteroidsFromDB()
                viewModel.asteroids?.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
            }

            R.id.view_today_asteroids -> {
                viewModel.getTodayAsteroidsFromDB()
                viewModel.asteroids?.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
            }

            R.id.view_saved_asteroids -> {
                viewModel.getSavedAsteroidsFromDB()
                viewModel.asteroids?.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
            }
        }
        return true
    }
}
