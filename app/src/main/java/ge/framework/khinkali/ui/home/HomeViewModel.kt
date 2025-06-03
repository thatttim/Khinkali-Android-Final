package ge.framework.khinkali.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.framework.khinkali.data.model.Recipe
import ge.framework.khinkali.data.repository.KhinkaliRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val trendingRecipes: List<Recipe> = emptyList(),
    val newRecipes: List<Recipe> = emptyList()
)

sealed class HomeEvent {
    data class OnRecipeClick(val recipeId: Int) : HomeEvent()
    object OnRetry : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: KhinkaliRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "HomeViewModel initialized")
        loadRecipes()
    }

    fun onEvent(event: HomeEvent) {
        Log.d(TAG, "Received event: $event")
        when (event) {
            is HomeEvent.OnRecipeClick -> {
                Log.d(TAG, "Navigating to recipe: ${event.recipeId}")
            }
            HomeEvent.OnRetry -> {
                Log.d(TAG, "Retrying to load recipes")
                loadRecipes()
            }
        }
    }

    private fun loadRecipes() {
        Log.d(TAG, "Starting to load recipes")
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                Log.d(TAG, "Calling repository.getHomeRecipes")
                
                repository.getHomeRecipes().collect { response ->
                    Log.d(TAG, "Received response: trending=${response.recipes_trending.size}, new=${response.recipes_new.size}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            trendingRecipes = response.recipes_trending,
                            newRecipes = response.recipes_new
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading recipes", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load recipes"
                    )
                }
            }
        }
    }
} 