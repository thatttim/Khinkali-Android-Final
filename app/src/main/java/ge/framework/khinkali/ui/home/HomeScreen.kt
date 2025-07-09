package ge.framework.khinkali.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ge.framework.khinkali.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "ხინკალი",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                actions = {
                    // Options Menu
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("განახლება") },
                            leadingIcon = { Icon(Icons.Default.Refresh, contentDescription = null) },
                            onClick = {
                                viewModel.onEvent(HomeEvent.OnRetry)
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("სორტირება") },
                            onClick = {
                                // TODO: Implement sorting functionality
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("პარამეტრები") },
                            leadingIcon = { Icon(Icons.Default.Settings, contentDescription = null) },
                            onClick = {
                                navController.navigate("info")
                                showMenu = false
                            }
                        )
                    }
                }
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> {
                LoadingIndicator(Modifier.padding(padding))
            }
            uiState.error != null -> {
                ErrorState(
                    message = uiState.error!!,
                    onRetry = { viewModel.onEvent(HomeEvent.OnRetry) },
                    modifier = Modifier.padding(padding)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {


                    // Trending Recipes Section

                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(uiState.trendingRecipes) { recipe ->
                                RecipeCard(
                                    recipe = recipe,
                                    onRecipeClick = { recipeId ->
                                        viewModel.onEvent(HomeEvent.OnRecipeClick(recipeId))
                                        navController.navigate("recipe/$recipeId")
                                    },
                                    modifier = Modifier
                                        .width(220.dp)
                                        .height(160.dp),
                                    isCompact = true,
                                )
                            }
                        }
                    }

                    // New Recipes Section
                    item {
                        SectionTitle(title = "შეიძლება მოგეწონოს",
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .fillMaxWidth()
                        )

                    }

                    item {
                        val gridHeight = (uiState.newRecipes.size + 1) / 2 * 236 // 220dp card height + 16dp spacing
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            userScrollEnabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(gridHeight.dp)
                        ) {
                            items(uiState.newRecipes) { recipe ->
                                RecipeCard(
                                    recipe = recipe,
                                    onRecipeClick = { recipeId ->
                                        viewModel.onEvent(HomeEvent.OnRecipeClick(recipeId))
                                        navController.navigate("recipe/$recipeId")
                                    },
                                    isCompact = true,
                                    modifier = Modifier
                                        .height(160.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
} 