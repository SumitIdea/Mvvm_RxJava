package com.example.mvvm_rxjava.retrofit

import com.example.mvvm_rxjava.data.Movies
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("popular?")
    fun getPopularMovies(
        @Query("api_key")
        api_key:String
    ):Observable<Movies>
}