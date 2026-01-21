package com.goldyonwar.bidoneassessment.data.repository

import com.goldyonwar.bidoneassessment.data.api.ApiService
import com.goldyonwar.bidoneassessment.domain.model.User
import com.goldyonwar.bidoneassessment.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: ApiService
) : UserRepository {

    override suspend fun getUsers(): Result<List<User>> {
        return try {
            val response = api.getUsers()

            val domainUsers = response.map { it.toDomain() }

            Result.success(domainUsers)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}