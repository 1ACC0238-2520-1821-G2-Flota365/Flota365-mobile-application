package pe.edu.upc.flota365.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Se declara la instancia de DataStore a nivel de archivo
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferencesRepository @Inject constructor(
  @ApplicationContext private val context: Context
) {
  // Definimos las claves para guardar los datos
  private object Keys {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
    val USER_ID = stringPreferencesKey("user_id")
  }

  // Flujo para obtener el token en tiempo real
  val authToken: Flow<String?> = context.dataStore.data.map { preferences ->
    preferences[Keys.AUTH_TOKEN]
  }

  // Flujo para obtener el ID del usuario
  val userId: Flow<String?> = context.dataStore.data.map { preferences ->
    preferences[Keys.USER_ID]
  }

  // Función para guardar el token y el ID del usuario después del login
  suspend fun saveAuthToken(token: String, userId: String) {
    context.dataStore.edit { preferences ->
      preferences[Keys.AUTH_TOKEN] = token
      preferences[Keys.USER_ID] = userId
    }
  }

  // Función para limpiar los datos al cerrar sesión
  suspend fun clear() {
    context.dataStore.edit { preferences ->
      preferences.clear()
    }
  }
}
