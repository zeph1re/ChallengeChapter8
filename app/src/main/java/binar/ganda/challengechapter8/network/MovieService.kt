package binar.ganda.challengechapter8.network

import binar.ganda.challengechapter8.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieService {

    @GET("movie/popular?api_key=5d31cc7e65f9c70956cd852dbd371b21")
    fun getPopularMovie() : Call<MovieResponse>
}