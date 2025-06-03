package ge.framework.khinkali.data.repository

import android.util.Log
import ge.framework.khinkali.data.api.KhinkaliApi
import ge.framework.khinkali.data.model.HomeResponse
import ge.framework.khinkali.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "KhinkaliRepository"

@Singleton
class KhinkaliRepository @Inject constructor(
    private val api: KhinkaliApi
) {
    fun getHomeRecipes(): Flow<HomeResponse> = flow {
        try {
            Log.d(TAG, "Fetching home recipes with lang=ka")
            val response = api.getHomeRecipes(lang = "ka")
            Log.d(TAG, "Successfully fetched home recipes")
            emit(response)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching home recipes", e)
            throw e
        }
    }

    fun getRecipeDetails(id: Int): Flow<Recipe> = flow {
        try {
            Log.d(TAG, "Fetching recipe details for id=$id, lang=ka")
            val response = api.getRecipeDetails(id = id, lang = "ka")
            Log.d(TAG, "Successfully fetched recipe details")
            emit(response)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching recipe details", e)
            throw e
        }
    }
} 