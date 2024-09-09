package com.example.unscramble.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unscramble.R
import com.example.unscramble.ui.theme.largeText

@Composable
fun Home(
    onNavigateToGame: () -> Unit,
) {
    
    val viewModel = hiltViewModel<HomeViewModel>()

    val userName by viewModel.userName.collectAsState()

    var userNameLocal by remember {
        mutableStateOf("")
    }

    if(userName.isEmpty()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.set_user_name),
                fontSize = largeText,
                fontWeight = FontWeight.Bold,
            )

            TextField(
                value = userNameLocal,
                onValueChange = { userNameLocal = it },
                label = {
                    Text(text = "Username")
                },
            )

            Button(onClick = { viewModel.saveToDataStore(userNameLocal) }) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = userName,
                fontSize = largeText,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = stringResource(id = (R.string.welcome)),
                fontSize = largeText,
            )

            Button(onClick = { onNavigateToGame() }) {
                Text(text = stringResource(id = R.string.start))
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    Home {}
}