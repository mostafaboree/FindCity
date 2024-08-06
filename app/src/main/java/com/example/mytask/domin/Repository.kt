package com.example.mytask.domin

import com.example.mytask.data.City

interface Repository {
    suspend fun getCities(json: String)
    fun searchCity(query: String): List<City>
}