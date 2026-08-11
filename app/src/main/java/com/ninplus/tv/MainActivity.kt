package com.ninplus.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NinPlusApp()
        }
    }
}

@Composable
fun NinPlusApp() {

    var screen by remember {
        mutableStateOf("login")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF252A40),
                        Color(0xFF0B0C11),
                        Color(0xFF050506)
                    )
                )
            )
    ) {

        when (screen) {

            "login" -> {
                LoginScreen(
                    onLogin = {
                        screen = "home"
                    }
                )
            }

            "home" -> {
                HomeScreen(
                    onShorts = {
                        screen = "shorts"
                    }
                )
            }

            "shorts" -> {
                ShortsScreen(
                    onBack = {
                        screen = "home"
                    }
                )
            }
        }
    }
}

@Composable
fun LoginScreen(
    onLogin: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(72.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Nin+",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        Text(
            text = "Войдите в Nin+",
            fontSize = 30.sp,
            color = Color.White
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Подключите аккаунт для персонального TV-интерфейса.",
            fontSize = 18.sp,
            color = Color.White.copy(alpha = 0.65f)
        )

        Spacer(
            modifier = Modifier.height(34.dp)
        )

        Button(
            onClick = onLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
        ) {

            Text(
                text = "Войти",
                fontSize = 24.sp
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "▣  Вход по QR-коду — подготовлено",
            fontSize = 18.sp,
            color = Color.White.copy(alpha = 0.65f)
        )
    }
}

@Composable
fun HomeScreen(
    onShorts: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(54.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Nin+",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Главная   Shorts   Подписки   Библиотека",
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.72f)
            )
        }

        Spacer(
            modifier = Modifier.height(46.dp)
        )

        Text(
            text = "Смотрите то, что вам нравится",
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(
            modifier = Modifier.height(34.dp)
        )

        Text(
            text = "Рекомендовано",
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            repeat(5) { index ->

                Box(
                    modifier = Modifier
                        .size(
                            width = 250.dp,
                            height = 140.dp
                        )
                        .background(
                            Color.White.copy(alpha = 0.09f),
                            RoundedCornerShape(18.dp)
                        )
                ) {

                    Text(
                        text = "Видео ${index + 1}",
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(36.dp)
        )

        Button(
            onClick = onShorts
        ) {

            Text(
                text = "Открыть Shorts",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ShortsScreen(
    onBack: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Shorts",
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Вертикальная лента Nin+ для TV",
                fontSize = 20.sp,
                color = Color.White.copy(alpha = 0.65f)
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Button(
                onClick = onBack
            ) {

                Text(
                    text = "Назад",
                    fontSize = 20.sp
                )
            }
        }
    }
}
