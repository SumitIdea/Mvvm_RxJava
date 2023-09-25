package com.example.mvvm_rxjava.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_rxjava.data.Movies
import com.example.mvvm_rxjava.data.Result
import com.example.mvvm_rxjava.repository.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val repository: MainRepository) : ViewModel()  {
    val movieList = MutableLiveData<List<Result>?>()
    val errorMessage = MutableLiveData<String>()
    lateinit var disposable: Disposable

    fun getAllMovies() {
        //observer subscribing to observable
        val response = repository.getAllMovies()
        response.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getMoviesListObserver())
    }

    private fun getMoviesListObserver(): Observer<Movies> {
    return object :Observer<Movies>{
        override fun onSubscribe(d: Disposable) {
            disposable = d
            //start showing progress indicator.
        }
        override fun onError(e: Throwable) {
            movieList.postValue(null)
        }
        override fun onComplete() {
            //hide progress indicator
        }
        override fun onNext(t: Movies) {
            movieList.postValue(t.results)
        }
      }
   }
}