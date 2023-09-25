package com.example.mvvm_rxjava.repository

import com.example.mvvm_rxjava.retrofit.Api
import com.example.mvvm_rxjava.retrofit.RetrofitInstance

class MainRepository constructor(private val api: Api) {
    fun getAllMovies() = api.getPopularMovies("69d66957eebff9666ea46bd464773cf0")
}