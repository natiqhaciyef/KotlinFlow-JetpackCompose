package com.natiqhaciyef.kotlinflow_jetpackcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){

    // Flow coroutine
    val countDownFlow = flow<Int> {
        var counter = 5
        emit(5)
        while (counter > 0){
            delay(1000)
            counter -= 1
            emit(counter)
        }
    }

    init {
        collectFlow()
        collectLatestFlow()
    }

    // Flow data collecting (listening changes)
    private fun collectFlow(){
        viewModelScope.launch {
            countDownFlow.collect{time ->
                delay(1500)
                println("Time is $time")
            }
        }
    }

    // Flow data collecting (only when last data comes the start collecting)
    private fun collectLatestFlow(){
        viewModelScope.launch {
            countDownFlow.collectLatest{time ->
                delay(1500)             // cancel emitting from flow because of two emit comes same time
                println("Time is $time - latest")
            }
        }
    }
     
}