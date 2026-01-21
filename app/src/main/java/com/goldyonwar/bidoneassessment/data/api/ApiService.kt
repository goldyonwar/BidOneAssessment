package com.goldyonwar.bidoneassessment.data.api

import com.goldyonwar.bidoneassessment.data.model.UserDto
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<UserDto>
}