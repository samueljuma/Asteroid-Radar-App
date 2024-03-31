package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.utils.Constants
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.utils.Filter
import com.udacity.asteroidradar.utils.getCurrentDate
import com.udacity.asteroidradar.utils.getEndDate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity  = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, MainViewModel.Factory(activity.application))[MainViewModel::class.java]
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

        //fetch Data from Internet : asteroids and Picture of Day
        viewModel.fetchAsteroids(getCurrentDate(), getEndDate(getCurrentDate()), BuildConfig.API_KEY)
        viewModel.fetchPictureOfDay()

        viewModel.setFilter(Filter.ALL)
        viewModel.asteroids.observe(viewLifecycleOwner){ asteroids->
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
        return when(item.itemId){
            R.id.menu_today -> {
                viewModel.setFilter(Filter.TODAY)
                Toast.makeText(context, "Today\'s Asteroids",Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_week -> {
                viewModel.setFilter(Filter.WEEK)
                Toast.makeText(context, "Weeks\'s Asteroids",Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_all -> {
                viewModel.setFilter(Filter.ALL)
                Toast.makeText(context, "All Asteroids",Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
