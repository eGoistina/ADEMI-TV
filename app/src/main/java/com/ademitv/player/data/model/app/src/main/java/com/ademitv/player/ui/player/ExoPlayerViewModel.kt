package com.ademitv.player.ui.player

import android.app.Application
import androidx.annotation.OptIn
import androidx.lifecycle.AndroidViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer

class ExoPlayerViewModel(application: Application) : AndroidViewModel(application) {
    @OptIn(UnstableApi::class)
    val player: ExoPlayer = ExoPlayer.Builder(application).build().apply {
        playWhenReady = true
    }

    fun playUrl(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}

