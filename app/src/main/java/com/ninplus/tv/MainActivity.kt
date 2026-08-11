package com.ninplus.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import com.ninplus.tv.engine.NavigationManager
import com.ninplus.tv.engine.NinWebView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NinPlus()
        }
    }
}

private enum class Page {
    HOME,
    SHORTS,
    SUBS,
    LIBRARY,
    SEARCH,
    ACCOUNT,
    SETTINGS
}

@Composable
private fun NinPlus() {

    var loggedIn by remember {
        mutableStateOf(false)
    }

    var page by remember {
        mutableStateOf(Page.HOME)
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    listOf(
                        Color(0xFF29324E),
                        Color(0xFF0A0C12),
                        Color(0xFF050608)
                    )
                )
            )
    ) {

        if (!loggedIn) {

            Login {
                loggedIn = true
                page = Page.HOME
            }

        } else {

            Shell(
                page = page,
                go = { page = it },
                logout = {
                    loggedIn = false
                }
            )
        }
    }
}

@Composable
private fun Login(
    onLogin: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(80.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Nin+",
            color = Color.White,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Войдите в Nin+",
            color = Color.White,
            fontSize = 30.sp
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Ваш персональный TV-пространство",
            color = Color.White.copy(.62f),
            fontSize = 18.sp
        )

        Spacer(Modifier.height(36.dp))

        Button(
            onClick = onLogin,

            modifier = Modifier
                .width(520.dp)
                .height(90.dp),

            colors = ButtonDefaults.colors(
                containerColor = Color.White.copy(.10f),
                focusedContainerColor = Color.White.copy(.25f),
                contentColor = Color.White
            )
        ) {

            Column {

                Text(
                    "Войти по коду",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    "QR-код и код подключения",
                    fontSize = 14.sp,
                    color = Color.White.copy(.6f)
                )
            }
        }

        Spacer(Modifier.height(22.dp))

        Text(
            "Официальная авторизация будет подключена следующим этапом.",
            color = Color.White.copy(.42f),
            fontSize = 15.sp
        )
    }
}

@Composable
private fun Shell(
    page: Page,
    go: (Page) -> Unit,
    logout: () -> Unit
) {

    val navManager = remember { NavigationManager() }

    BackHandler(enabled = page != Page.HOME) {
        if (navManager.canGoBack()) {
            navManager.goBack()
        } else {
            go(Page.HOME)
        }
    }

    Column(
        Modifier.fillMaxSize()
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .height(86.dp)
                .padding(horizontal = 55.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "Nin+",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.width(38.dp))

            listOf(
                Page.HOME to "Главная",
                Page.SHORTS to "Shorts",
                Page.SUBS to "Подписки",
                Page.LIBRARY to "Библиотека",
                Page.SEARCH to "Поиск"
            ).forEach { (p, name) ->

                Nav(
                    text = name,
                    selected = page == p
                ) {
                    go(p)
                }

                Spacer(Modifier.width(5.dp))
            }

            Spacer(Modifier.weight(1f))

            Nav(
                "Аккаунт",
                page == Page.ACCOUNT
            ) {
                go(Page.ACCOUNT)
            }

            Spacer(Modifier.width(5.dp))

            Nav(
                "⚙",
                page == Page.SETTINGS
            ) {
                go(Page.SETTINGS)
            }
        }

        when (page) {

            Page.HOME ->
                Home {
                    go(Page.SHORTS)
                }

            Page.SHORTS ->
                NinWebView(
                    url = "https://www.youtube.com/shorts",
                    modifier = Modifier.fillMaxSize(),
                    navigationManager = navManager
                )

            Page.SUBS ->
                NinWebView(
                    url = "https://www.youtube.com/feed/subscriptions",
                    modifier = Modifier.fillMaxSize(),
                    navigationManager = navManager
                )

            Page.LIBRARY ->
                NinWebView(
                    url = "https://www.youtube.com/feed/library",
                    modifier = Modifier.fillMaxSize(),
                    navigationManager = navManager
                )

            Page.SEARCH ->
                NinWebView(
                    url = "https://www.youtube.com/",
                    modifier = Modifier.fillMaxSize(),
                    navigationManager = navManager
                )

            Page.SETTINGS ->
                CenterPage(
                    "Настройки",
                    "Настройки Nin+"
                )

            Page.ACCOUNT -> {

                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        "Аккаунт",
                        color = Color.White,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(25.dp))

                    Button(
                        onClick = logout
                    ) {
                        Text("Выйти")
                    }
                }
            }
        }
    }
}

@Composable
private fun Nav(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        colors = ButtonDefaults.colors(
            containerColor =
            if (selected)
                Color.White.copy(.16f)
            else
                Color.Transparent,

            focusedContainerColor =
            Color.White.copy(.28f),

            contentColor = Color.White
        ),

        contentPadding =
        PaddingValues(
            horizontal = 14.dp,
            vertical = 7.dp
        )
    ) {

        Text(
            text,
            fontSize = 15.sp
        )
    }
}

@Composable
private fun Home(
    onShorts: () -> Unit
) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(55.dp)
    ) {

        Text(
            "Для вас",
            color = Color.White,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Подборка Nin+ для большого экрана",
            color = Color.White.copy(.6f),
            fontSize = 18.sp
        )

        Spacer(Modifier.height(30.dp))

        Text(
            "Рекомендовано",
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(15.dp))

        LazyRow(
            horizontalArrangement =
            Arrangement.spacedBy(18.dp)
        ) {

            items((1..6).toList()) {
                VideoCard(it)
            }
        }

        Spacer(Modifier.height(28.dp))

        Button(
            onClick = onShorts
        ) {

            Text(
                "Открыть Shorts",
                fontSize = 19.sp
            )
        }
    }
}

@Composable
private fun VideoCard(
    number: Int
) {

    var focused by remember {
        mutableStateOf(false)
    }

    Box(
        Modifier
            .size(300.dp, 170.dp)

            .onFocusChanged {
                focused = it.isFocused
            }

            .focusable()

            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF3A4C78),
                        Color(0xFF151A29)
                    )
                ),
                RoundedCornerShape(20.dp)
            )

            .border(
                if (focused) 3.dp else 0.dp,
                Color.White.copy(.9f),
                RoundedCornerShape(20.dp)
            )
    ) {

        Column(
            Modifier
                .align(Alignment.BottomStart)
                .padding(17.dp)
        ) {

            Text(
                "Видео $number",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                "Nin+",
                color = Color.White.copy(.58f),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun CenterPage(
    title: String,
    subtitle: String
) {

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment =
            Alignment.CenterHorizontally
        ) {

            Text(
                title,
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Text(
                subtitle,
                color = Color.White.copy(.6f),
                fontSize = 20.sp
            )
        }
    }
}
