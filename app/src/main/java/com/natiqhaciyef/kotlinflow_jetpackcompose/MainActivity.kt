package com.natiqhaciyef.kotlinflow_jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natiqhaciyef.kotlinflow_jetpackcompose.ui.theme.KotlinFlowJetpackComposeTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.collectLatestStateLifecycleFlow(viewModel.stateFlow){number ->
            // send data to the XML UI
        }

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.stateFlow.collectLatest {
                    // send data to the XML UI
                }
            }
        }


        setContent {
            KotlinFlowJetpackComposeTheme {
                val viewModel = viewModel<MainViewModel>()
                val stateFlow = viewModel.stateFlow.collectAsState()
//                val timer = viewModel.countDownFlow.collectAsState(initial = 10)
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "" /*"${timer.value}"*/,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
        }
    }
}

fun <T> ComponentActivity.collectLatestStateLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit){
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            flow.collectLatest(collect)
        }
    }
}


