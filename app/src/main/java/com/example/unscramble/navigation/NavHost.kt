package com.example.unscramble.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.unscramble.game.Game
import com.example.unscramble.home.Home
import com.example.unscramble.notifications.Notifications
import com.example.unscramble.profile.Profile
import com.example.unscramble.profile.friends.Friends
import com.example.unscramble.ranking.Ranking
import com.example.unscramble.scores.Scores
import com.example.unscramble.profile.settings.Settings

@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = UnscrambleScreen.Home.name,
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 10.dp)
    ) {
        composable(route = UnscrambleScreen.Home.name) {
            Home(
                onNavigateToGame = { navController.navigate(UnscrambleScreen.Game.name) }
            )
        }
        composable(route = UnscrambleScreen.Game.name) {
            Game()
        }
        composable(route = UnscrambleScreen.Notifications.name) {
            Notifications()
        }
        composable(route = UnscrambleScreen.Ranking.name) {
            Ranking()
        }
        composable(route = UnscrambleScreen.Scores.name) {
            Scores()
        }
        composable(route = UnscrambleScreen.Profile.name) {
            Profile(
                onNavigateToFriends = { navController.navigate(UnscrambleScreen.Friends.name) }
            )
        }
        composable(route = UnscrambleScreen.Friends.name) {
            Friends()
        }
        composable(route = UnscrambleScreen.Settings.name) {
            Settings()
        }
    }
}