package com.example.mytask.domin.useCase

import com.example.mytask.domin.Repository

class LoadCity(val repo: Repository) {
     suspend operator fun invoke(json: String) = repo.getCities(json)
}