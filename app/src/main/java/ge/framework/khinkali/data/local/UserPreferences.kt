package ge.framework.khinkali.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferencesKeys {
        val USER_ID = intPreferencesKey("user_id")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_SURNAME = stringPreferencesKey("user_surname")
        val USER_AVATAR = stringPreferencesKey("user_avatar")
        val USER_LANGUAGE = stringPreferencesKey("user_language")
    }

    val userId: Flow<Int?> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_ID]
    }

    val userEmail: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_EMAIL]
    }

    val userName: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_NAME]
    }

    val userSurname: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_SURNAME]
    }

    val userAvatar: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_AVATAR]
    }

    val userLanguage: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_LANGUAGE] ?: "ka" // Default to Georgian
    }

    suspend fun saveUserId(userId: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = userId
        }
    }

    suspend fun saveUserProfile(email: String, name: String, surname: String, avatar: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_EMAIL] = email
            preferences[PreferencesKeys.USER_NAME] = name
            preferences[PreferencesKeys.USER_SURNAME] = surname
            preferences[PreferencesKeys.USER_AVATAR] = avatar
        }
    }

    suspend fun saveUserLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_LANGUAGE] = language
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
} 