package com.example.unscramble.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unscramble.R
import com.example.unscramble.ui.theme.Pink40
import com.example.unscramble.ui.theme.PurpleGrey40
import com.example.unscramble.ui.theme.PurpleGrey80
import com.example.unscramble.ui.theme.largeText
import com.example.unscramble.ui.theme.mediumText

@Composable
fun Ranking() {

    val viewModel = hiltViewModel<RankingViewModel>()

    val ranking by viewModel.ranking.collectAsState()
    val loading by viewModel.loadingRanking.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()

    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("General", "Personal")

    if(loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center),
                color = PurpleGrey40,
                trackColor = PurpleGrey80,
            )
        }
    } else if(showRetry) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                fontSize = largeText,
                fontWeight = FontWeight.Bold,
            )
            Text(text = stringResource(id = R.string.retry_load_ranking))
            Button(onClick = { viewModel.retryLoadingRanking() }) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }
            when (tabIndex) {
                0 ->
                    LazyColumn {
                        items(ranking) { rank ->
                            RankingView(ranking = rank)
                        }
                    }
                1 ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Your personal best is:",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            text = "99",
                            fontSize = 22.sp
                        )
                    }
            }
        }
    }
}

@Composable
fun RankingView(
    ranking: RankingModel,
    modifier: Modifier = Modifier,
) {
    val icon = if(ranking.plays_from_argentina) Icons.Filled.Home else Icons.Filled.LocationOn

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
    ) {
        Text(ranking.name, fontSize = mediumText)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(ranking.ranking.toString(), fontSize = largeText, color = Pink40)
            Icon(imageVector = icon, contentDescription = null)
        }
    }
    HorizontalDivider()
}

@Preview
@Composable
fun PreviewRankingView() {
    RankingView(ranking = RankingModel("Katia Cammisa", 99, true))
}