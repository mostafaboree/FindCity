package com.example.mytask.data

import com.example.mytask.domin.Repository
import com.example.mytask.domin.Trie
import com.google.gson.Gson

class ImPRepositoryCity(private val trie: Trie): Repository {
    override suspend fun getCities(json: String){
        val cities = Gson().fromJson(json, Array<City>::class.java).toList()
        cities.forEach{
            trie.insert(it)
        }

    }

    override fun searchCity(query: String): List<City> {
        return trie.search(query)
    }
}