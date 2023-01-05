package com.example.compose.rally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.rally.ui.components.RallyTabRow
import com.example.compose.rally.ui.theme.RallyTheme

/**
 * This Activity recreates part of the Rally Material Study from
 * https://material.io/design/material-studies/rally.html
 */

// https://www.valueof.io/blog/compose-compositionlocal-compositionlocalprovider
// https://stackoverflow.com/questions/72481883/jetpack-compose-deeplink-navigation-from-oauth
// https://proandroiddev.com/best-practices-for-deeplinking-in-android-1dc1ea060c0c
// https://blog.branch.io/universal-links-uri-schemes-app-links-and-deep-links-whats-the-difference/
// https://stackoverflow.com/questions/72337085/jetpack-compose-navigations-deep-links-hit-customtabsintent-and-flash-to-deskto
// https://help.adjust.com/en/article/test-adjust-deep-links

// https://medium.com/@debduttapanda/jetpack-compose-power-navigation-ad5b0189af0d

// https://stackoverflow.com/questions/72481883/jetpack-compose-deeplink-navigation-from-oauth
class RallyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyTheme {
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStack?.destination

                val currentScreen: RallyDestination = rallyTabRowScreens
                    .find { it.route == currentDestination?.route } ?: Overview

                Scaffold(
                    topBar = {
                        RallyTabRow(
                            allScreens = rallyTabRowScreens,
                            onTabSelected = { newScreen ->
                                navController.navigateSingleTopTo(newScreen.route)
                            },
                            currentScreen = currentScreen
                        )
                    }
                ) { innerPadding ->
                    RallyNavHost(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }
}