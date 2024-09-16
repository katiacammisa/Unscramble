package com.example.unscramble.profile.friends

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unscramble.common.DismissBackground
import com.example.unscramble.data.Friend

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Friends() {
    val viewModel = hiltViewModel<FriendsViewModel>()
    val friendsList by viewModel.friendList.collectAsState(initial = listOf())

    var showAlertDialog by remember {
        mutableStateOf(false)
    }

    fun showAlertDialog() {
        showAlertDialog = true
    }

    if(showAlertDialog) {
        BasicAlertDialog(onDismissRequest = { showAlertDialog = false }) {
            Card {
                Text(
                    text = "Your friend data is not complete",
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AddFriendForms(
            showAlertDialog = ::showAlertDialog,
            addFriend = viewModel::addFriend
        )
        friendsList.forEach { friend ->
            FriendCard(
                friend,
                viewModel::deleteFriend,
                viewModel::favoriteFriend,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun FriendsPreview() {
    Friends()
}

@Composable
fun AddFriendForms(
    showAlertDialog: () -> Unit,
    addFriend: (String, String, String) -> Unit,
    modifier: Modifier = Modifier,
) {

    var friendName by remember {
        mutableStateOf("")
    }
    var friendEmail by remember {
        mutableStateOf("")
    }
    var friendAge by remember {
        mutableStateOf("")
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        TextField(
            value = friendName,
            onValueChange = { friendName = it },
            label = {
                Text(text = "Name")
            },
        )
        TextField(
            value = friendEmail,
            onValueChange = { friendEmail = it },
            label = {
                Text(text = "Email")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        TextField(
            value = friendAge,
            onValueChange = { friendAge = it },
            label = {
                Text(text = "Age")
            },
        )

        Button(onClick = {
            if(friendName.isNotEmpty() && friendEmail.isNotEmpty() && friendAge.isNotEmpty()) {
                addFriend(friendName, friendEmail, friendAge)
                friendName = ""
                friendEmail = ""
                friendAge = ""
            } else {
                showAlertDialog()
            }
        },
            modifier = modifier,
        ) {
            Text(text = "Save friend")
        }
    }
}

@Preview
@Composable
fun PreviewAddFriendForms() {
    AddFriendForms(
        showAlertDialog = {},
        addFriend = {_, _, _ -> }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendCard(
    friend: Friend,
    onRemove: (Friend) -> Unit,
    onFavorite: (Friend) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when(it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onRemove(friend)
                    Toast.makeText(context, "Friend deleted", Toast.LENGTH_SHORT).show()
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    onFavorite(friend)
                    Toast.makeText(context, "Friend saved to favorites", Toast.LENGTH_SHORT).show()
                }
                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        // positional threshold of 25%
        positionalThreshold = { it * .25f }
    )


    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { DismissBackground(dismissState = dismissState) },
    ) {
        Row(
            modifier = modifier.background(color = Color.LightGray).padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(text = friend.name)
                Text(text = friend.email)
            }
            Text(text = friend.age)
        }
    }
}

@Preview
@Composable
fun PreviewFriendCard() {
    FriendCard(
        friend = Friend(name = "Katia Cammisa", email = "katia@gmail.com", age = "24", favorite = false),
        {},
        {},
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    )
}