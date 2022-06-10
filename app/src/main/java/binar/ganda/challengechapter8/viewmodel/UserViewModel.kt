package binar.ganda.challengechapter8.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.ganda.challengechapter8.model.DefaultResponse
import binar.ganda.challengechapter8.model.ResponseAllUserItem
import binar.ganda.challengechapter8.model.ResponseUser
import binar.ganda.challengechapter8.network.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val service : UserService) : ViewModel(){
    private val userState = MutableStateFlow(emptyList<ResponseAllUserItem>())
    val dataUserState: StateFlow<List<ResponseAllUserItem>> get() = userState

    init {
        viewModelScope.launch {
            val user = service.getAllUser()
            userState.value = user
        }
    }

    fun insertNewUser(requestUser: ResponseUser): Boolean {
        var messageResponse = false
        service.registerUser(requestUser)
            .enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    messageResponse = response.isSuccessful
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    messageResponse = false
                }

            })
        return messageResponse
    }





}
