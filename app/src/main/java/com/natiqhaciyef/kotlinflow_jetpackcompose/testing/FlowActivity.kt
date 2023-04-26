package com.natiqhaciyef.kotlinflow_jetpackcompose.testing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natiqhaciyef.kotlinflow_jetpackcompose.MainViewModel
import com.natiqhaciyef.kotlinflow_jetpackcompose.testing.ui.theme.KotlinFlowJetpackComposeTheme

class FlowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinFlowJetpackComposeTheme {
                val viewModel = viewModel<FlowViewModel>()
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "${viewModel.numberFlow.collectAsState(initial = 10).value}" ,
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


