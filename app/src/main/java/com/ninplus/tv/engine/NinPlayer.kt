package com.ninplus.tv.engine

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.widget.VideoView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

/**
 * Базовый NIN Player.
 *
 * Пока НЕ привязан к YouTube-потокам.
 * Использует стандартный VideoView для тестовых видео.
 *
 * В будущем будет заменён на ExoPlayer с поддержкой:
 * - Play/Pause/Seek
 * - Progress и Volume
 * - Fullscreen
 * - D-pad управления
 */
class NinPlayer(context: Context) {
    private val videoView = VideoView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    fun setVideoUri(uri: Uri) {
        videoView.setVideoURI(uri)
    }

    fun play() = videoView.start()
    fun pause() = videoView.pause()
    fun seekTo(ms: Int) = videoView.seekTo(ms)
    fun getCurrentPosition(): Int = videoView.currentPosition
    fun getDuration(): Int = videoView.duration
    fun isPlaying(): Boolean = videoView.isPlaying

    fun getView(): VideoView = videoView
}

@Composable
fun NinPlayerView(
    modifier: Modifier = Modifier,
    onPlayerReady: (NinPlayer) -> Unit = {}
) {
    val context = LocalContext.current
    val player = remember { NinPlayer(context) }

    AndroidView(
        factory = { player.getView() },
        modifier = modifier
    )

    DisposableEffect(Unit) {
        onPlayerReady(player)
        onDispose { }
    }
}
