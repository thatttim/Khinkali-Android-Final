package ge.framework.khinkali.di

import com.google.gson.GsonBuilder
import ge.framework.khinkali.data.api.KhinkaliApi
import ge.framework.khinkali.data.api.RecipeTypeAdapter
import ge.framework.khinkali.data.model.Recipe
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-API-KEY", KhinkaliApi.API_KEY)
                .build()
            chain.proceed(request)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val gson = GsonBuilder()
        .registerTypeAdapter(Recipe::class.java, RecipeTypeAdapter())
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(KhinkaliApi.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api: KhinkaliApi = retrofit.create(KhinkaliApi::class.java)
} 