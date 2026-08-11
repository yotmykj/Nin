package com.ninplus.tv.engine

import android.webkit.WebView
import java.lang.ref.WeakReference

/**
 * NavigationManager управляет навигацией WebView.
 *
 * Используется для:
 * - Обработки кнопки Back (goBack vs выход из WebView)
 * - Программной навигации (reload, loadUrl)
 *
 * Работает через WeakReference, чтобы не удерживать WebView в памяти.
 */
class NavigationManager {
    private var webViewRef: WeakReference<WebView>? = null

    fun attach(webView: WebView) {
        webViewRef = WeakReference(webView)
    }

    fun detach() {
        webViewRef = null
    }

    private fun webView(): WebView? = webViewRef?.get()

    fun canGoBack(): Boolean = webView()?.canGoBack() ?: false

    fun goBack() {
        webView()?.goBack()
    }

    fun reload() {
        webView()?.reload()
    }

    fun loadUrl(url: String) {
        webView()?.loadUrl(url)
    }
}
