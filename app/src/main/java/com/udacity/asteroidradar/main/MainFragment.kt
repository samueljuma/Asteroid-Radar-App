package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var adapter: AsteroidItemAdapter

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
         binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        binding.lifecycleOwner = this

        adapter = AsteroidItemAdapter(AsteroidClickListener { asteroid ->
            viewModel.onAsteroidClicked(asteroid)
        })

        binding.asteroidRecycler.adapter = adapter

        binding.asteroidRecycler.layoutManager = LinearLayoutManager(context)
        viewModel.listOfAsteroids.observe(viewLifecycleOwner){ asteroids->
            asteroids?.let {
                adapter.submitList(asteroids)
            }
        }

        viewModel.navigateToDetails.observe(viewLifecycleOwner){
            it?.let {
                findNavController().navigate(
                    MainFragmentDirections.actionShowDetail(it)
                )
                viewModel.doneNavigatingToDetails()
            }
        }




        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
