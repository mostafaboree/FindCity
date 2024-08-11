package com.example.mytask.Presentation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mytask.data.City
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
//import

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ShowInMap(city: City,
              animatedVisibilityScope: AnimatedVisibilityScope,
              transition: SharedTransitionScope,


              onCLick: ()->Unit){
    val lat = LatLng(city.coord.lat,city.coord.lon)
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(lat,15f)
    }
    with(transition){
    GoogleMap(cameraPositionState = cameraPositionState , modifier = Modifier
        .fillMaxWidth()
        .sharedBounds(
            rememberSharedContentState(key =city._id),
            animatedVisibilityScope
        )
    ) {

        MarkerInfoWindow(
            state = MarkerState(position = lat),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .border(
                        BorderStroke(1.dp, Color.Black),
                        RoundedCornerShape(10)
                    )
                    .clip(RoundedCornerShape(10))
                    .background(Color.Blue)
                    .padding(20.dp)
            ) {
                Text(city.name +  " " + city.country, fontWeight = FontWeight.Bold, color = Color.White)
                Text(city.coord.lat.toString() + " " + city.coord.lon.toString(), fontWeight = FontWeight.Medium, color = Color.White)
            }
        }



    }}
}
