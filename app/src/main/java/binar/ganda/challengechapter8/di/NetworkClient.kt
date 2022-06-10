package binar.ganda.challengechapter8.di

import binar.ganda.challengechapter8.network.MovieService
import binar.ganda.challengechapter8.network.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkClient {
    private const val USER_URL = "https://6254434c19bc53e2347b93f1.mockapi.io/"
    private const val MOVIE_URL = "https://api.themoviedb.org/3/"

    private val logging : HttpLoggingInterceptor
        get(){
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }

        }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    @Provides
    @Singleton
    @Named("User")
    fun provideUserRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(USER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideUserApi(@Named("User")retrofitUser: Retrofit): UserService =
        retrofitUser.create(UserService::class.java)


    @Provides
    @Singleton
    fun provideMovieRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(MOVIE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)
}