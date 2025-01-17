package com.asetec.presentation.ui.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.PratikFagadiya.smoothanimationbottombar.model.SmoothAnimationBottomBarScreens
import com.asetec.presentation.R
import com.asetec.presentation.animation.Screens
import com.asetec.presentation.component.BottomNavigationBar
import com.asetec.presentation.route.ScreenNavigationConfiguration
import com.asetec.presentation.ui.main.home.screen.HomeScreen
import com.asetec.presentation.ui.main.home.screen.ProfileScreen

@Composable
fun RootScreen() {

    val context = LocalContext.current

    val bottomNavigationItems = listOf(
        SmoothAnimationBottomBarScreens(
            Screens.HomeScreen.route,
            stringResource(id = R.string.nav_home),
            R.drawable.baseline_home_24
        ),
        SmoothAnimationBottomBarScreens(
            Screens.AnalyzeScreen.route,
            stringResource(id = R.string.nav_analyze),
            R.drawable.baseline_bar_chart_24
        ),
        SmoothAnimationBottomBarScreens(
            Screens.ProfileScreen.route,
            stringResource(id = R.string.nav_profile),
            R.drawable.baseline_person_24
        )
    )

    val navController = rememberNavController()
    val currentIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = bottomNavigationItems,
                currentIndex = currentIndex,
                navController = navController
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            ScreenNavigationConfiguration(
                navController = navController,
                context = context
            )
        }
    }
}