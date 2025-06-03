package ge.framework.khinkali.ui.recipe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ge.framework.khinkali.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeId: Int,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val layoutDirection = LocalLayoutDirection.current
    val scrollState = rememberScrollState()

    LaunchedEffect(recipeId) {
        viewModel.loadRecipeDetails()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("რეცეპტი",  style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = paddingValues.calculateStartPadding(layoutDirection),
                    top = paddingValues.calculateTopPadding(),
                    end = paddingValues.calculateEndPadding(layoutDirection),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            when {
                uiState.isLoading -> {
                    LoadingIndicator()
                }
                !uiState.error.isNullOrEmpty() -> {
                    ErrorState(
                        message = uiState.error ?: "An unknown error occurred",
                        onRetry = { viewModel.loadRecipeDetails() }
                    )
                }
                uiState.recipe != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(16.dp)
                    ) {
                        RecipeHeader(recipe = uiState.recipe!!)
                        Spacer(modifier = Modifier.height(16.dp))
                        RecipeInfo(recipe = uiState.recipe!!)
                        Spacer(modifier = Modifier.height(16.dp))
                        IngredientsList(ingredients = uiState.recipe!!.ingredients)
                        Spacer(modifier = Modifier.height(16.dp))
                        InstructionsList(instructions = uiState.recipe!!.instructions)
//                        Spacer(modifier = Modifier.height(16.dp))
//                        AuthorInfo(author = uiState.recipe!!.author)
                    }
                }
            }
        }
    }
} 