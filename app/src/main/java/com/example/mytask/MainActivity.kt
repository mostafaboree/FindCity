package com.example.mytask

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.mytask.Presentation.DataScreen
import com.example.mytask.Presentation.ShowInMap
import com.example.mytask.Presentation.ViewModelCity
import com.example.mytask.data.City
import com.example.mytask.data.ImPRepositoryCity
import com.example.mytask.domin.Trie
import com.example.mytask.domin.useCase.CityUseCase
import com.example.mytask.domin.useCase.FilterCity
import com.example.mytask.domin.useCase.LoadCity
import com.example.mytask.ui.theme.MyTaskTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalSharedTransitionApi::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = R.raw.cities
        val trie = Trie()
        val jsonString = resources.openRawResource(data).bufferedReader().use { it.readText() }
        val repo = ImPRepositoryCity(trie)
        enableEdgeToEdge()
        setContent {
            var screen by remember { mutableStateOf<Screen>(Screen.City) }
            var search by remember { mutableStateOf("") }
            var cities by remember { mutableStateOf<List<City>>(emptyList()) }
            var city by remember { mutableStateOf<City?>(null) }
            val viewModel = ViewModelCity(CityUseCase(LoadCity(repo), FilterCity(repo)), jsonString)
            viewModel.cities.observe(this) {
                cities = it
            }

            MyTaskTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {

                    FloatingActionButton(onClick = {
                        screen = Screen.City
                    }) {
                        Text(text = if (screen is Screen.City) "Main" else "Back")

                    }}

                ) { innerPadding ->

                    Column(modifier = Modifier.padding(innerPadding)) {
                        SharedTransitionLayout {
                            AnimatedContent(targetState = screen, label = "MainScreen") {
                                when (it) {
                                    Screen.City -> {
                                        DataScreen(
                                            list = cities,
                                            sharedTransitionScope = this@SharedTransitionLayout,
                                            animatedVisibilityScope =this@AnimatedContent ,
                                            onCLick ={
                                                city = it
                                                screen = Screen.Map}
                                        ) {
                                            search = it
                                            viewModel.searchCity(it)
                                        }
                                    }
                                    Screen.Map -> {
                                        ShowInMap(
                                        city!!,
                                            animatedVisibilityScope = this@AnimatedContent,
                                            transition = this@SharedTransitionLayout
                                        ) {
                                            screen = Screen.City
                                        }
                                    }

                                }

                            }


                        }
                    } } }
        }
    }
}
sealed class Screen(){
    object Map:Screen()
    object City:Screen()
}
