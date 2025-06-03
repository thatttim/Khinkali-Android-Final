package ge.framework.khinkali.data.api

import android.util.Log
import com.google.gson.*
import ge.framework.khinkali.data.model.HomeResponse
import ge.framework.khinkali.data.model.Instruction
import ge.framework.khinkali.data.model.Recipe
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

class RecipeTypeAdapter : JsonDeserializer<Recipe> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Recipe {
        val jsonObject = json.asJsonObject
        
        // Create a new JsonObject without the instructions field
        val newJsonObject = JsonObject()
        jsonObject.entrySet().forEach { (key, value) ->
            if (key != "instructions") {
                newJsonObject.add(key, value)
            }
        }
        
        // Parse instructions from string to List<Instruction> if present
        val instructionsElement = jsonObject.get("instructions")
        if (instructionsElement != null && !instructionsElement.isJsonNull) {
            val instructionsStr = instructionsElement.asString
            val instructions = try {
                val gson = Gson()
                gson.fromJson(instructionsStr, Array<Instruction>::class.java).toList()
            } catch (e: Exception) {
                Log.e("RecipeTypeAdapter", "Error parsing instructions", e)
                null
            }
            
            // Add the parsed instructions as a JsonArray
            if (instructions != null) {
                val instructionsArray = JsonArray()
                instructions.forEach { instruction ->
                    val instructionObj = JsonObject()
                    instructionObj.addProperty("step", instruction.step)
                    instructionObj.addProperty("instruction", instruction.instruction)
                    instructionsArray.add(instructionObj)
                }
                newJsonObject.add("instructions", instructionsArray)
            }
        }
        
        // Use Gson to deserialize the modified JsonObject
        return Gson().fromJson(newJsonObject, Recipe::class.java)
    }
}

interface KhinkaliApi {
    companion object {
        const val BASE_URL = "https://khinkali.framework.ge/api/v3/"
        const val API_KEY = "27e2e4852d5ca075d3594d3ab511d89a5568996a836b3fb77e473af0ebd99a7e"
        private const val TAG = "KhinkaliApi"

        fun create(): KhinkaliApi {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("X-API-Key", API_KEY)
                        .build()
                    Log.d(TAG, "Making request to: ${request.url}")
                    chain.proceed(request)
                }
                .build()

            val gson = GsonBuilder()
                .registerTypeAdapter(Recipe::class.java, RecipeTypeAdapter())
                .create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(KhinkaliApi::class.java)
        }
    }

    @GET("general.php")
    suspend fun getHomeRecipes(
        @Query("action") action: String = "home",
        @Query("lang") lang: String = "ka"
    ): HomeResponse

    @GET("general.php")
    suspend fun getRecipeDetails(
        @Query("action") action: String = "details",
        @Query("id") id: Int,
        @Query("lang") lang: String = "ka"
    ): Recipe
} 