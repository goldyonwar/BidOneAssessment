package com.goldyonwar.bidoneassessment.domain.model

import androidx.compose.runtime.Immutable

@Immutable //to optimize Jetpack Compose performance (Skipping Recomposition).
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val website: String,
    val companyName: String
)