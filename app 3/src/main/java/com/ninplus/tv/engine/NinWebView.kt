package com.ninplus.tv.engine

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

/**
 * NinWebView — TV-оптимизированный WebView для NIN+.
 *
 * Поддерживает:
 * - JavaScript, DOM storage, cookies
 * - Hardware acceleration
 * - Focus и D-pad навигацию
 * - Базовую обработку ошибок через NinWebViewClient
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun NinWebView(
    url: String,
    modifier: Modifier = Modifier,
    navigationManager: NavigationManager? = null
) {
    val context = LocalContext.current
    val webView = remember(url) {
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                databaseEnabled = true
                cacheMode = WebSettings.LOAD_DEFAULT
                mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                useWideViewPort = true
                loadWithOverviewMode = true
                mediaPlaybackRequiresUserGesture = false
                userAgentString = "Mozilla/5.0 (Linux; Android 14; TV) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/120.0.0.0 Safari/537.36"
            }

            webViewClient = NinWebViewClient()
            webChromeClient = NinWebChromeClient()

            isFocusable = true
            isFocusableInTouchMode = true

            // Запрашиваем focus для D-pad навигации
            post { requestFocus() }
        }
    }

    DisposableEffect(url) {
        webView.loadUrl(url)
        navigationManager?.attach(webView)
        onDispose {
            navigationManager?.detach()
        }
    }

    AndroidView(
        factory = { webView },
        modifier = modifier
    )
}
