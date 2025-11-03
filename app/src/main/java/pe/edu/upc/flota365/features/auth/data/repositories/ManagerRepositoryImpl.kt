package pe.edu.upc.flota365.features.auth.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.auth.data.remote.models.ManagerRegisterRequestDto
import pe.edu.upc.flota365.features.auth.data.remote.services.AuthService
import javax.inject.Inject

class ManagerRepositoryImpl @Inject constructor(
  private val authService: AuthService
) {
  /**
   * Registra un nuevo manager en el contexto de autenticación
   */
  suspend fun registerManagerAndUser(request: ManagerRegisterRequestDto): Resource<Unit> =
    withContext(Dispatchers.IO) {
      try {
        val response = authService.registerUser(request)

        if (response.isSuccessful) {
          Resource.Success(Unit)
        } else {
          Resource.Error("Error HTTP ${response.code()}: ${response.message()}")
        }
      } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Error desconocido")
      }
    }
}
