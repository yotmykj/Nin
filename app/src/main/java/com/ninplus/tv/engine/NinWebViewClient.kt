package com.ninplus.tv.engine

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * Кастомный WebViewClient для NIN+.
 *
 * На первом этапе:
 * - Разрешает загрузку внутри WebView
 * - Обрабатывает окончание загрузки страницы
 *
 * В будущем здесь будет:
 * - shouldInterceptRequest для Mod System
 * - Блокировка рекламы
 * - Инжекция скриптов
 */
class NinWebViewClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        // false = загружаем URL внутри этого WebView
        return false
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        // Точка входа для будущего ScriptInjector
    }
}
