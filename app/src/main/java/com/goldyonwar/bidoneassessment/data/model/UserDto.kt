package com.goldyonwar.bidoneassessment.data.model

import com.goldyonwar.bidoneassessment.domain.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "email") val email: String?,
    @Json(name = "phone") val phone: String?,
    @Json(name = "website") val website: String?,
    @Json(name = "company") val company: CompanyDto?
) {
    fun toDomain(): User {
        return User(
            id = id ?: 0,
            name = name ?: "Unknown",
            email = email ?: "No Email",
            phone = phone ?: "No Phone",
            website = website ?: "",
            companyName = company?.name ?: "No Company"
        )
    }
}

@JsonClass(generateAdapter = true)
data class CompanyDto(
    @Json(name = "name") val name: String?,
    @Json(name = "catchPhrase") val catchPhrase: String?,
    @Json(name = "bs") val bs: String?
)