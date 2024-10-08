package com.example.unscramble.navigation

enum class UnscrambleScreen {
    Home,
    Game,

    Notifications,

    Ranking,
    Scores,

    Profile,
    Friends,
    Settings,
}

val basePages = listOf(
    UnscrambleScreen.Home.name,
    UnscrambleScreen.Ranking.name,
    UnscrambleScreen.Profile.name,
)