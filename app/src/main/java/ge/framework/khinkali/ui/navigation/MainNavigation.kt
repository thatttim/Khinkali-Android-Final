package ge.framework.khinkali.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ge.framework.khinkali.ui.home.HomeScreen
import ge.framework.khinkali.ui.info.AppInfoScreen
import ge.framework.khinkali.ui.recipe.RecipeDetailScreen

sealed class Screen(val route: String, val icon: @Composable () -> Unit, val label: String) {
    object Home : Screen("home", { Icon(Icons.Default.Home, contentDescription = "Home") }, "მთავარი")
    object Info : Screen("info", { Icon(Icons.Default.Info, contentDescription = "Info") }, "ინფო")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Info)
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = screen.icon,
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { _ ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.Info.route) {
                AppInfoScreen()
            }
            composable(
                route = "recipe/{recipeId}",
                arguments = listOf(
                    androidx.navigation.navArgument("recipeId") {
                        type = androidx.navigation.NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: return@composable
                RecipeDetailScreen(
                    navController = navController,
                    recipeId = recipeId
                )
            }
        }
    }
} 