package com.example.compose.rally

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

/**
 * Contract for information needed on every Rally navigation destination
 */
interface RallyDestination {
    val icon: ImageVector
    val route: String
}

/**
 * Rally app navigation destinations
 */
object Overview : RallyDestination {
    override val icon = Icons.Filled.PieChart
    override val route = "overview"
}

object Accounts : RallyDestination {
    override val icon = Icons.Filled.AttachMoney
    override val route = "accounts"
}

object Bills : RallyDestination {
    override val icon = Icons.Filled.MoneyOff
    override val route = "bills"
}

object SingleAccount : RallyDestination {
    // Added for simplicity, this icon will not in fact be used, as SingleAccount isn't
    // part of the RallyTabRow selection
    override val icon = Icons.Filled.Money
    override val route = "single_account"
    const val accountTypeArg = "account_type"

    val routeWithArgs = "$route/{$accountTypeArg}"

    val arguments = listOf(
        navArgument(accountTypeArg) {
            type = NavType.StringType
            defaultValue = "0"
        }
    )

    val deepLinks = listOf(
        navDeepLink { uriPattern = "rally://$routeWithArgs" }
    )
}

// Screens to be displayed in the top RallyTabRow
val rallyTabRowScreens = listOf(Overview, Accounts, Bills)

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateToSingleAccount(accountType: String) {
    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
}