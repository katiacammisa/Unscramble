package com.example.unscramble.profile

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Profile(
    onNavigateToFriends: () -> Unit,
) {
    Text(text = "This is the profile")
    Button(onClick = onNavigateToFriends, Modifier.wrapContentHeight()) {
        Text(text = "Navigate to friends")
    }
}