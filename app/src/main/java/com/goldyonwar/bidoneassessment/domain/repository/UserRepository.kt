package com.goldyonwar.bidoneassessment.domain.repository

import com.goldyonwar.bidoneassessment.domain.model.User

interface UserRepository {
    suspend fun getUsers(): Result<List<User>>
}