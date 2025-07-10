package ge.framework.khinkali.di

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ge.framework.khinkali.data.api.KhinkaliApi
import ge.framework.khinkali.data.local.NotesDataStore
import ge.framework.khinkali.data.repository.KhinkaliRepository
import ge.framework.khinkali.data.repository.NotesRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val TAG = "AppModule"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKhinkaliApi(): KhinkaliApi {
        Log.d(TAG, "Creating KhinkaliApi instance")
        return KhinkaliApi.create()
    }

    @Provides
    @Singleton
    fun provideKhinkaliRepository(api: KhinkaliApi): KhinkaliRepository {
        Log.d(TAG, "Creating KhinkaliRepository instance")
        return KhinkaliRepository(api)
    }

    @Provides
    @Singleton
    fun provideNotesDataStore(@ApplicationContext context: Context): NotesDataStore {
        Log.d(TAG, "Creating NotesDataStore instance")
        return NotesDataStore(context)
    }

    @Provides
    @Singleton
    fun provideNotesRepository(notesDataStore: NotesDataStore): NotesRepository {
        Log.d(TAG, "Creating NotesRepository instance")
        return NotesRepository(notesDataStore)
    }
} 