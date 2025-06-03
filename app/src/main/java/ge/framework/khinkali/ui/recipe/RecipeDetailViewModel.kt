package ge.framework.khinkali.ui.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.framework.khinkali.data.model.Recipe
import ge.framework.khinkali.data.repository.KhinkaliRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecipeDetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val recipe: Recipe? = null
)

sealed class RecipeDetailEvent {
    object OnRetry : RecipeDetailEvent()
}

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: KhinkaliRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recipeId: Int = checkNotNull(savedStateHandle["recipeId"])

    private val _uiState = MutableStateFlow(RecipeDetailUiState())
    val uiState: StateFlow<RecipeDetailUiState> = _uiState.asStateFlow()

    init {
        loadRecipeDetails()
    }

    fun onEvent(event: RecipeDetailEvent) {
        when (event) {
            RecipeDetailEvent.OnRetry -> {
                loadRecipeDetails()
            }
        }
    }

    fun loadRecipeDetails() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                repository.getRecipeDetails(recipeId).collect { recipe ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            recipe = recipe
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load recipe details"
                    )
                }
            }
        }
    }
} 