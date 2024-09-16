package com.example.unscramble.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unscramble.R
import com.example.unscramble.ui.theme.largeText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    onNavigateToGame: () -> Unit,
) {
    
    val viewModel = hiltViewModel<HomeViewModel>()

    val userName by viewModel.userName.collectAsState()

    var userNameLocal by remember {
        mutableStateOf("")
    }

    var showBottomModal by remember {
        mutableStateOf(false)
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
        Box {
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
            FloatingActionButton(
                onClick = { showBottomModal = !showBottomModal },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 10.dp)
            ) {
                Icon(
                    imageVector =
                    if(showBottomModal) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = ""
                )
            }
            if(showBottomModal) {
                ModalBottomSheet(onDismissRequest = { showBottomModal = !showBottomModal }) {
                    Text(
                        text = "I am in a bottom sheet",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(30.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    Home {}
}