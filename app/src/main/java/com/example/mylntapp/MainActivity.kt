package com.example.mylntapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mylntapp.data.repository.LntRepository
import com.example.mylntapp.ui.screens.*
import com.example.mylntapp.ui.theme.MyLNTAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyLNTAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            MainScreen(
                                onTeachersClick = { navController.navigate("teachers") },
                                onNewsClick = { LntRepository.openNews(this@MainActivity) },
                                onScheduleClick = { LntRepository.openSchedule(this@MainActivity) },
                                onLibraryClick = { navController.navigate("library") }
                            )
                        }
                        composable("teachers") { TeachersScreen() }
                        composable("library") { LibraryScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    onTeachersClick: () -> Unit,
    onNewsClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onLibraryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ЛНТ",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MenuButton(
                text = "Преподаватели",
                icon = R.drawable.ic_info,
                onClick = onTeachersClick,
                modifier = Modifier.weight(1f).padding(4.dp)
            )
            MenuButton(
                text = "Новости",
                icon = R.drawable.ic_news,
                onClick = onNewsClick,
                modifier = Modifier.weight(1f).padding(4.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MenuButton(
                text = "Расписание",
                icon = R.drawable.ic_schedule,
                onClick = onScheduleClick,
                modifier = Modifier.weight(1f).padding(4.dp)
            )
            MenuButton(
                text = "Библиотека",
                icon = R.drawable.ic_library,
                onClick = onLibraryClick,
                modifier = Modifier.weight(1f).padding(4.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuButton(
    text: String,
    icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = text,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }
} 