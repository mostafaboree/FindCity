package com.example.mytask.Presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytask.data.City
import com.example.mytask.domin.useCase.CityUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewModelCity (private val cityCase:CityUseCase,private val json:String): ViewModel() {
    private val _cities = MutableLiveData<List<City>>(emptyList())
    val cities:LiveData<List<City>> = _cities as LiveData<List<City>>
    private val _searchtext = MutableLiveData("")
    var searchtext= _searchtext as LiveData<String>
init {
    viewModelScope.launch(Dispatchers.IO) {
    getCities()
    }
}
     suspend fun  getCities(){
              cityCase.getCities(json)
            _cities.postValue(cityCase.searchCity(""))

    }
    fun searchCity(text:String){
            _searchtext.value=text
            _cities.value=(cityCase.searchCity(text))

    }


}