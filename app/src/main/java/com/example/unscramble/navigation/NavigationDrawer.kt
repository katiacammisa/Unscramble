package com.example.unscramble.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NavigationDrawerSheet(
    onClose: () -> Unit,
) {
    ModalDrawerSheet {
        Column(modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp)) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "",
                modifier = Modifier.clickable { onClose() }
            )
            Spacer(modifier = Modifier.size(10.dp))
            NavigationDrawerItem(
                label = {
                    Row {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "")
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(text = "Cart")
                    }
                },
                selected = true,
                onClick = {},
                shape = RoundedCornerShape(10.dp),
            )
            Spacer(modifier = Modifier.size(10.dp))
            NavigationDrawerItem(
                label = {
                    Row {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(text = "Delete")
                    }
                },
                selected = false,
                onClick = {},
                shape = RoundedCornerShape(10.dp),
            )
            Spacer(modifier = Modifier.size(10.dp))
            NavigationDrawerItem(
                label = {
                    Row {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "")
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(text = "Notifications")
                    }
                },
                selected = false,
                onClick = {},
                shape = RoundedCornerShape(10.dp),
            )
        }
    }
}