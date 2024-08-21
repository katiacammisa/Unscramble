package com.example.unscramble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.unscramble.navigation.BottomBar
import com.example.unscramble.navigation.NavHostComposable
import com.example.unscramble.navigation.TopBar
import com.example.unscramble.ui.theme.UnscrambleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            UnscrambleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopBar(
                                navController = navController,
                            )
                        },
                        bottomBar = {
                            BottomBar { navController.navigate(it) }
                        },
                    ) { innerPadding ->
                        NavHostComposable(innerPadding, navController)
                    }
                }
            }
        }
    }
}