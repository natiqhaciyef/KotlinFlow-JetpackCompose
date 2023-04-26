package com.natiqhaciyef.kotlinflow_jetpackcompose.testing

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class FlowViewModel : ViewModel() {
    val numberFlow = flow<Int> {
        var number = 10
        emit(number)
        while (number > 0) {
            delay(1000)
            number -= 1
            emit(number)
        }
    }

    val stateFlow = MutableStateFlow(0)

    init {
        filterByDivide5()
    }

    fun filterByDivide5() {
        viewModelScope.launch {
            numberFlow
                .filter {
                    it % 2 == 0
                }
                .collect { number ->
                    stateFlow.value = number
                }
        }
    }

}