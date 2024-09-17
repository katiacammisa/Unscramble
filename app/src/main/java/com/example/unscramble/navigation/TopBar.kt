package com.example.unscramble.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.unscramble.common.TabBarBadgeView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val showBackButton by remember(currentBackStackEntry) {
        derivedStateOf {
            navController.previousBackStackEntry != null && !basePages.contains(navController.currentDestination?.route)
        }
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {
            Column(modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp)) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier.clickable { scope.launch { drawerState.close() } }
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
    }, drawerState = drawerState) {
        TopAppBar(
            navigationIcon = {
                Icon(
                    imageVector = if(!showBackButton) Icons.Filled.Menu else Icons.Filled.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable {
                            if (showBackButton) {
                                navController.popBackStack()
                            } else {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        }
                )
            },
            title = {
                Text(text = "Unscramble")
            },
            actions = {
                Row(horizontalArrangement = Arrangement.spacedBy(25.dp)) {
                    BadgedBox(badge = { TabBarBadgeView(7) }) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "",
                            modifier = Modifier.clickable { navController.navigate(UnscrambleScreen.Notifications.name) }
                        )
                    }
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "",
                    )
                }
            },
            modifier = Modifier.padding(horizontal = 10.dp),
        )
    }
}