package com.ademitv.player

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.ademitv.player.data.model.Channel
import com.ademitv.player.ui.player.ExoPlayerViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ExoPlayerViewModel by viewModels()

    private val sampleChannels = listOf(
        Channel("1", "Macedonia TV (Demo)", "https://via.placeholder.com/150", "General", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"),
        Channel("2", "Torbeši Stream (Demo)", "https://via.placeholder.com/150", "Culture", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MainScreen(viewModel = viewModel, channels = sampleChannels)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: ExoPlayerViewModel, channels: List<Channel>) {
    var selectedChannel by remember { mutableStateOf(channels.first()) }

    LaunchedEffect(selectedChannel) {
        viewModel.playUrl(selectedChannel.streamUrl)
    }

    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxHeight()
        ) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = viewModel.player
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            Text(text = "АДЕМИ ТВ - Каналы", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(channels) { channel ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedChannel = channel },
                        colors = CardDefaults.cardColors(
                            containerColor = if (channel == selectedChannel)
                                MaterialTheme.colorScheme.primaryContainer
                            else
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = channel.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = channel.groupTitle, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

