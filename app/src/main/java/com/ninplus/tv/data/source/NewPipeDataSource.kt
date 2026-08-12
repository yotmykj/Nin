package com.ninplus.tv.data.source

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.helpers.ExtractorHelper
import org.schabi.newpipe.extractor.stream.StreamInfo

/**
 * Минимальный data source для YouTube через NewPipe Extractor (KMP-версия).
 *
 * На этом этапе — только proof of concept:
 * - Получение trending videos
 * - Получение metadata одного видео
 * - Логирование результатов в Logcat
 *
 * НЕ использует Room, ViewModel, Hilt — всё синхронно/корутинно.
 */
class NewPipeDataSource {

    companion object {
        private const val TAG = "NinPipe"
        private const val SERVICE_ID = 0 // YouTube
    }

    /**
     * Получает список trending видео с YouTube.
     * Результат логируется в Logcat с тегом NinPipe.
     */
    suspend fun fetchTrending(): List<org.schabi.newpipe.extractor.InfoItem> = withContext(Dispatchers.IO) {
        val service = ServiceList.YouTube
        val extractor = service.getKioskList().getDefaultKioskExtractor(null)
        extractor.fetchPage()
        val items = extractor.initialPage.items

        Log.i(TAG, "=== Trending videos: ${items.size} ===")
        items.forEachIndexed { index, item ->
            val streamItem = item as? org.schabi.newpipe.extractor.stream.StreamInfoItem
            Log.i(TAG, "[$index] id=${streamItem?.url}" +
                    " | title=${streamItem?.name}" +
                    " | channel=${streamItem?.uploaderName}" +
                    " | duration=${streamItem?.duration}" +
                    " | thumbnail=${streamItem?.thumbnailUrl}")
        }

        items
    }

    /**
     * Получает детальную информацию о видео и его потоки.
     * Результат логируется в Logcat с тегом NinPipe.
     */
    suspend fun fetchVideoInfo(videoUrl: String): StreamInfo? = withContext(Dispatchers.IO) {
        try {
            val info = ExtractorHelper.getStreamInfo(SERVICE_ID, videoUrl)

            Log.i(TAG, "=== Video Info ===")
            Log.i(TAG, "title=${info.name}")
            Log.i(TAG, "channel=${info.uploaderName}")
            Log.i(TAG, "duration=${info.duration}")
            Log.i(TAG, "thumbnail=${info.thumbnailUrl}")
            Log.i(TAG, "viewCount=${info.viewCount}")

            Log.i(TAG, "=== Video Streams: ${info.videoStreams.size} ===")
            info.videoStreams.forEach { stream ->
                Log.i(TAG, "  video: ${stream.resolution} | ${stream.format} | ${stream.url?.take(80)}...")
            }

            Log.i(TAG, "=== Audio Streams: ${info.audioStreams.size} ===")
            info.audioStreams.forEach { stream ->
                Log.i(TAG, "  audio: ${stream.averageBitrate}kbps | ${stream.format} | ${stream.url?.take(80)}...")
            }

            info
        } catch (e: Exception) {
            Log.e(TAG, "Failed to fetch video info: ${e.message}", e)
            null
        }
    }
}
