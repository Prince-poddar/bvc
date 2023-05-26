package com.example.bvc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bvc.databinding.ActivityMainBinding
import com.example.bvc.di.ui.adapter.MovieAdapter
import com.example.bvc.networking.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.example.bvc.networking.Status
import com.example.bvc.response.Search
import com.example.bvc.util.Constants


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private val moviesViewModel: MoviesViewModel by viewModels()
    private var moviesList = ArrayList<Search>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rvMovies.layoutManager = LinearLayoutManager(this)
        adapter = MovieAdapter(this, moviesList)
        binding.rvMovies.adapter = adapter

        searchTextWatcher()

        setUpObservers()

        binding.tilSearch.setEndIconOnClickListener {
            binding.editText.setText("")
            moviesList.clear()
            adapter.notifyDataSetChanged()
        }


    }

    private fun searchTextWatcher() {
        binding.editText.doAfterTextChanged {
            if (it != null) {
                if (binding.editText.text.toString().trim().isNotEmpty()) {
                    binding.progressBar.visibility = View.VISIBLE
                    hitMoviesApi()
                    binding.rvMovies.visibility = View.VISIBLE
                } else {
                    binding.rvMovies.visibility = View.GONE
                }


            }
        }
    }

    private fun hitMoviesApi() {
        moviesViewModel.getMoviesListFromApi(
            Constants.apiKey,
            binding.editText.text.toString().trim(),
            Constants.type
        )

    }

    override fun onResume() {
        super.onResume()
    }

    private fun setUpObservers() {
        moviesViewModel.moviesResponse.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { response ->
                            setMoviesData(response.Search)

                        }
                    }

                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE

                    }

                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE

                    }
                }
            }
        }

    }

    private fun setMoviesData(search: ArrayList<Search>) {
        moviesList.clear()
        if (search.isNotEmpty()) {
            moviesList.addAll(search)
        } else {

        }
        adapter.notifyDataSetChanged()

    }


}