package com.example.mytask.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.jar.Attributes.Name

class TestViewModel: ViewModel() {
    private val _filter = MutableLiveData<String>()
    val filter: LiveData<String> = _filter
    private val _test = MutableLiveData<List<String>>()
    val test: LiveData<List<String>> = _test
     var Names: List<String>
    init {

          Names= listOf("ASC","BFD","CDE","EFG","HIJ","KLM","NOP","QRS","TUV","WXY","ZDWE","Cft","D" +
                "gMV","HLK","IDS","JBV","VCK","LJK","M","N","O","P","Q","R","S")
        getNames()


    }
    fun getNames(){
        _test.value=Names
    }




    fun setFilter(text: String){
        _filter.value=text
        _test.value=Names.filter {
            it.contains(_filter.value.toString(),true)
        }
    }









}