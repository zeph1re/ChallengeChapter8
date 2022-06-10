package binar.ganda.challengechapter8.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import binar.ganda.challengechapter8.model.MovieListItem
import binar.ganda.challengechapter8.model.MovieResponse
import binar.ganda.challengechapter8.network.MovieService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(service : MovieService) : ViewModel() {

    private val movieState = MutableStateFlow(emptyList<MovieListItem>())
    val dataMovieState : StateFlow<List<MovieListItem>> get() = movieState

    init {
        service.getPopularMovie()
        .enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    movieState.value = response.body()!!.results
                } else {
                    Log.e("view_model", response.message())
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("view_model_error", t.message.toString())
            }

        })
    }

}

