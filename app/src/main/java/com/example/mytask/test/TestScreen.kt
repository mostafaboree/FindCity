package com.example.mytask.test

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytask.data.City
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    val coroutineScope= rememberCoroutineScope()
    var searchValue by remember { mutableStateOf("") }
    val viewModel=TestViewModel()

    var names by remember {mutableStateOf<List<String>>(emptyList())}
    viewModel.test.observeForever {it->
        names=it

    }
    MainScreenTest(names){
        searchValue=it
      // viewModel.setFilter(searchValue)
    }
}

@Composable
fun Greeting(name: String) {

}
@Composable
fun MainScreenTest(list:List<String>,
               onValueChange:(String)->Unit) {
    var searchValue by  remember { mutableStateOf("") }

    Column {
        Search(onValueChange)

        CityList(list = list)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(onchange:(String)->Unit) {
    var search by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {

        OutlinedTextField(
            value = search, onValueChange = {
                search = it
                onchange(it)}, modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Row {
                    Text(text ="Search" )
                }

            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier.size(40.dp),
                    tint = Color.LightGray
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color(0xFF9290C3),
            ),
            shape = RoundedCornerShape(20.dp)
        )


    }
}
@Composable
fun CityList(list: List<String>){
    LazyColumn {
        items(list){city->
            CityItem(title = city , subtitle = city.length.toString(), modifier = Modifier)
        }
    }
}
@Composable
private fun CityItem(title:String,subtitle:String,modifier: Modifier){
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp)
        .background(Color.LightGray)){
        Text(text = title)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = subtitle)

    }

}
