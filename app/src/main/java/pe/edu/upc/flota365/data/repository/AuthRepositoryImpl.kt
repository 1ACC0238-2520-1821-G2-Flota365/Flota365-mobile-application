package pe.edu.upc.flota365.data.repository

import pe.edu.upc.flota365.data.remote.service.AuthService
import pe.edu.upc.flota365.domain.model.User
import pe.edu.upc.flota365.domain.repository.AuthRepository
import pe.edu.upc.flota365.data.remote.dto.LoginRequest
import pe.edu.upc.flota365.data.remote.dto.RegisterRequest
import pe.edu.upc.flota365.data.remote.dto.UserDto
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
  private val api: AuthService
) : AuthRepository {

  override suspend fun login(email: String, password: String): User {
    val request = LoginRequest(email, password)
    return safeApiCall { api.login(request).toDomain() }
  }

  override suspend fun register(name: String, email: String, password: String): User {
    val request = RegisterRequest(name, email, password)
    return safeApiCall { api.register(request).toDomain() }
  }

  override suspend fun getProfile(userId: String, token: String): User {
    return safeApiCall {
      val response: UserDto = api.getProfile(userId, "Bearer $token")
      response.toDomain()
    }
  }

  /**
   * Manejador básico de errores de red para evitar que la app se caiga
   * en caso de error HTTP o fallo de conexión.
   */
  private suspend fun <T> safeApiCall(apiCall: suspend () -> T): T {
    try {
      return apiCall()
    } catch (e: HttpException) {
      throw Exception("Error del servidor (${e.code()}): ${e.message()}")
    } catch (e: IOException) {
      throw Exception("Error de conexión. Verifica tu conexión a Internet.")
    } catch (e: Exception) {
      throw Exception("Error desconocido: ${e.message}")
    }
  }
}
