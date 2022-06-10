package binar.ganda.challengechapter8.network

import binar.ganda.challengechapter8.model.DefaultResponse
import binar.ganda.challengechapter8.model.ResponseAllUserItem
import binar.ganda.challengechapter8.model.ResponseUser
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    //user
    //user
    @GET("datauserlogin")
    suspend fun getAllUser(): List<ResponseAllUserItem>

    @POST("datauserlogin")
    fun registerUser(@Body requestUser: ResponseUser): Call<DefaultResponse>

}