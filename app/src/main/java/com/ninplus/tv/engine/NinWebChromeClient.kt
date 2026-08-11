package com.ninplus.tv.engine

import android.webkit.WebChromeClient

/**
 * Кастомный WebChromeClient для NIN+.
 *
 * На первом этапе — базовая реализация.
 *
 * В будущем здесь будет:
 * - Обработка fullscreen видео
 * - Прогресс загрузки
 * - JavaScript alerts / confirms
 */
class NinWebChromeClient : WebChromeClient() {
    // Базовая реализация. Расширяется на следующих этапах.
}
