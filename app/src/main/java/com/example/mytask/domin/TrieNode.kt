package com.example.mytask.domin

import com.example.mytask.data.City
import java.util.Locale

class TrieNode {
    val children = mutableMapOf<Char, TrieNode>()
    var isEndOfWord = false
    var city: City? = null
}

class Trie {
    private val root = TrieNode()

    fun insert(city: City) {
        var node = root
        for (char in city.name.toLowerCase(Locale.ROOT)) {
            val child = node.children.getOrPut(char) { TrieNode() }
            node = child
        }
        node.isEndOfWord = true
        node.city = city
    }

    fun search(prefix: String): List<City> {
        var node = root
        for (char in prefix.toLowerCase()) {
            node = node.children[char] ?: return emptyList()
        }
        val results = mutableListOf<City>()
        dfs(node, results)
        return results
    }

    private fun dfs(node: TrieNode, results: MutableList<City>) {
        if (node.isEndOfWord) {
            results.add(node.city!!)
        }
        for (child in node.children.values) {
            dfs(child, results)
        }
    }
}
