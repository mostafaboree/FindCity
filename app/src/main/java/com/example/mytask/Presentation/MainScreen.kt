package com.example.mytask.Presentation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mytask.data.City


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
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DataScreen(list: List<City>,
               sharedTransitionScope: SharedTransitionScope,
               animatedVisibilityScope: AnimatedVisibilityScope,
               onCLick: (city:City) -> Unit,
               onchange:(String)->Unit){
    Column {
        Search(onchange)
    LazyColumn {
        items(list){city->
            CityItem(city, modifier = Modifier,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                onCLick = onCLick)
        }
    }}
}
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
 private fun CityItem(city: City,
                      modifier: Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
                      ,onCLick:(city:City)->Unit  ){
     with(sharedTransitionScope){
    Column(modifier = modifier
        .sharedBounds(
            rememberSharedContentState(key = city._id),
            animatedVisibilityScope
        )
        .clickable {
            onCLick(city)
        }
        .fillMaxWidth()
        .padding(10.dp)
        .background(Color.LightGray)){
        Text(text = city.name  + city.country)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = city.coord.lat.toString()  +  city.coord.lon.toString())

    }

}}
