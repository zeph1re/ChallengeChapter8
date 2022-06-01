package binar.ganda.challengechapter8.network

import binar.ganda.challengechapter8.model.MovieListItem
import retrofit2.Call
import retrofit2.http.GET

interface MovieService {
    @GET("movie/{movie_id}")
    fun getMovieDetails() : Call<List<MovieListItem>>

    @GET("movie/popular?api_key=5d31cc7e65f9c70956cd852dbd371b21")
    fun getPopularMovie() : List<MovieListItem>
}