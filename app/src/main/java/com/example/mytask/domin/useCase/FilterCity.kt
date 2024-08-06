package com.example.mytask.domin.useCase

import androidx.compose.runtime.referentialEqualityPolicy
import com.example.mytask.domin.Repository

class FilterCity(private val repository: Repository) {
    operator fun invoke(prfix: String)= repository.searchCity(prfix)
}