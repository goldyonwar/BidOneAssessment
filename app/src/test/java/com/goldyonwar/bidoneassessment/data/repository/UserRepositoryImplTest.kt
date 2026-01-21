package com.goldyonwar.bidoneassessment.data.repository

import com.goldyonwar.bidoneassessment.data.api.ApiService
import com.goldyonwar.bidoneassessment.data.model.CompanyDto
import com.goldyonwar.bidoneassessment.data.model.UserDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UserRepositoryImplTest {

    private val apiService: ApiService = mockk()

    private val repository = UserRepositoryImpl(apiService)

    private val fakeUserDto = UserDto(
        id = 1,
        name = "Gold",
        email = "gold@test.com",
        phone = "021123456",
        website = "test.com",
        company = CompanyDto("BidOne", "CatchPhrase", "BS")
    )

    @Test
    fun `getUsers success - should return mapped domain objects`() = runTest {
        coEvery { apiService.getUsers() } returns listOf(fakeUserDto)

        val result = repository.getUsers()

        assertTrue(result.isSuccess)
        val users = result.getOrNull()
        assertEquals(1, users?.size)

        assertEquals("Gold", users?.first()?.name)
        assertEquals("BidOne", users?.first()?.companyName)
    }

    @Test
    fun `getUsers failure - should return failure result`() = runTest {
        val errorMessage = "Server Down"
        coEvery { apiService.getUsers() } throws Exception(errorMessage)

        val result = repository.getUsers()

        assertTrue(result.isFailure)
        assertEquals(errorMessage, result.exceptionOrNull()?.message)
    }

}