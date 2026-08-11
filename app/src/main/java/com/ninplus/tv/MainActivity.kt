package com.ninplus.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
        setContent { NinPlusApp() }
    }
}

@Composable
fun NinPlusApp() {
    var screen by remember { mutableStateOf("login") }

    Box(
        Modifier.fillMaxSize().background(
            Brush.radialGradient(
                listOf(Color(0xFF252A40), Color(0xFF0B0C11), Color(0xFF050506))
            )
        )
    ) {
        when (screen) {
            "login" -> LoginScreen { screen = "home" }
            "home" -> HomeScreen { screen = "shorts" }
            "shorts" -> ShortsScreen { screen = "home" }
        }
    }
}

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(72.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Nin+", fontSize = 56.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(Modifier.height(14.dp))
        Text("Войдите в Nin+", fontSize = 30.sp, color = Color.White)
        Spacer(Modifier.height(10.dp))
        Text(
            "Подключите аккаунт для персонального TV-интерфейса.",
            fontSize = 18.sp, color = Color.White.copy(alpha = .65f)
        )
        Spacer(Modifier.height(34.dp))

        Button(
            onClick = onLogin,
            modifier = Modifier.width(430.dp).height(76.dp),
            shape = androidx.tv.material3.ButtonDefaults.shape(
                RoundedCornerShape(22.dp)
            )
        ) {
            Text("Войти", fontSize = 24.sp)
        }

        Spacer(Modifier.height(24.dp))
        Text("▣  Вход по QR-коду — подготовлено", fontSize = 18.sp,
            color = Color.White.copy(alpha = .65f))
    }
}

@Composable
fun HomeScreen(onShorts: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(54.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Nin+", fontSize = 34.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Главная   Shorts   Подписки   Библиотека",
                fontSize = 18.sp, color = Color.White.copy(alpha = .72f))
        }
        Spacer(Modifier.height(46.dp))
        Text("Смотрите то, что вам нравится", fontSize = 38.sp,
            fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(Modifier.height(34.dp))
        Text("Рекомендовано", fontSize = 26.sp, fontWeight = FontWeight.SemiBold,
            color = Color.White)
        Spacer(Modifier.height(18.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
            repeat(5) { i ->
                Box(Modifier.size(250.dp, 140.dp)
                    .background(Color.White.copy(alpha = .09f), RoundedCornerShape(18.dp))) {
                    Text("Видео ${i + 1}", Modifier.align(Alignment.BottomStart).padding(16.dp),
                        color = Color.White, fontSize = 18.sp)
                }
            }
        }
        Spacer(Modifier.height(36.dp))
        Button(onClick = onShorts) { Text("Открыть Shorts", fontSize = 20.sp) }
    }
}

@Composable
fun ShortsScreen(onBack: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Shorts", fontSize = 44.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(Modifier.height(18.dp))
            Text("Вертикальная лента Nin+ для TV", fontSize = 20.sp,
                color = Color.White.copy(alpha = .65f))
            Spacer(Modifier.height(28.dp))
            Button(onClick = onBack) { Text("Назад", fontSize = 20.sp) }
        }
    }
}
