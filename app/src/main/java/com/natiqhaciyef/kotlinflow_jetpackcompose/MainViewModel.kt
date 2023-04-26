package com.natiqhaciyef.kotlinflow_jetpackcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // Flow coroutine -> cold flow
    val countDownFlow = flow<Int> {
        var counter = 5
        emit(5)
        while (counter > 0) {
            delay(1000)
            counter -= 1
            emit(counter)
        }
    }

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
//        collectFlow()
//        collectLatestFlow()
//        firstSpecificOperators()
        secondSpecificOperators()
    }


    // Flow data collecting (only when last data comes the start collecting)
    private fun collectLatestFlow() {
        viewModelScope.launch {
            countDownFlow.collectLatest { time ->
                delay(1500)             // cancel emitting from flow because of two emit comes same time
                println("Time is $time - latest")
            }
        }
    }

    // Flow data collecting (listening changes)
    private fun collectFlow() {
        viewModelScope.launch {
            countDownFlow
                .collect { time ->
                    println("Time is $time")
                }
        }
    }


    // operators
    private fun firstSpecificOperators() {
        countDownFlow.onEach {
            println(it)
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            countDownFlow
                .filter { time ->
                    time % 2 == 0
                }
                .map { it * it }
                .onEach {
                    println(it)
                }
        }
    }


    private fun secondSpecificOperators() {
        val customFlow = flow {
            emit(50)
            delay(500)
            emit(30)
        }
        viewModelScope.launch {
            customFlow
//                .count { it > 2 }     // counting values which value > 2
//                .reduce { counter, value -> counter + value }         // counter = 0, calculating value with counter
//                .fold(100) { counter, value -> counter + value }    // counter = 100, calculating value with counter
                .flatMapConcat { value ->
                    flow {
                        emit(value + 5)
                        delay(500)
                        emit(value + 10)
                    }
//                .buffer()    // connecting async delay with flow and collect
//                .conflate()    // connecting async delay with flow when crush something it gets only latest parameter
                }.collect{
                    println(it)
                }
        }
    }
}