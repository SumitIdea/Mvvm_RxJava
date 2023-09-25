package com.example.mvvm_rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mvvm_rxjava.adapter.MovieAdapter
import com.example.mvvm_rxjava.databinding.ActivityMainBinding
import com.example.mvvm_rxjava.repository.MainRepository
import com.example.mvvm_rxjava.retrofit.RetrofitInstance
import com.example.mvvm_rxjava.viewmodel.MainViewModel
import com.example.mvvm_rxjava.viewmodel.MyViewModelFactory

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private lateinit var movieAdapter: MovieAdapter

    //    private val retrofitService = RetrofitInstance.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieAdapter = MovieAdapter()
        initRecyclerView()
        viewModel()
    }

    override fun onDestroy() {
        //don't send events  once the activity is destroyed
        viewModel.disposable.dispose()
        super.onDestroy()
    }
    private fun initRecyclerView() {
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = movieAdapter

        }
    }
    private fun viewModel() {
        //get viewModel instance using ViewModelProvider.Factory
        viewModel =
            ViewModelProvider(this, MyViewModelFactory(MainRepository(RetrofitInstance.api))).get(
                MainViewModel::class.java
            )
        viewModel.getAllMovies()

        //the observer will only receive events if the owner(activity) is in active state
        //invoked when movieList data changes
        viewModel.movieList.observe(this, Observer {
            if (it != null) {
                Log.d(TAG, "movieList: $it")
                movieAdapter.setMovieList(it)
            } else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })

        //invoked when a network exception occurred
        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "errorMessage: $it")
        })
    }
}