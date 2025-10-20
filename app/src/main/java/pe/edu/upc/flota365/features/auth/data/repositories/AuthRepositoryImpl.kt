package pe.edu.upc.flota365.features.auth.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.auth.data.remote.models.*
import pe.edu.upc.flota365.features.auth.data.remote.services.AuthService
import pe.edu.upc.flota365.features.auth.domain.models.User
import pe.edu.upc.flota365.features.auth.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
  private val service: AuthService
) : AuthRepository {

  override suspend fun login(email: String, password: String): Resource<User> =
    withContext(Dispatchers.IO) {
      try {
        val response = service.login(LoginRequestDto(email, password))

        if (response.isSuccessful) {
          val body = response.body()
          if (body != null) {
            val user = User(
              id = body.id,
              firstName = body.firstName,
              lastName = body.lastName,
              fullName = body.fullName,
              email = body.email,
              role = body.role,
              isActive = body.isActive,
              createdAt = body.createdAt,
              updatedAt = body.updatedAt
            )
            Resource.Success(user)
          } else {
            Resource.Error("Respuesta vacía del servidor")
          }
        } else {
          Resource.Error("Error HTTP ${response.code()}: ${response.message()}")
        }
      } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Error desconocido")
      }
    }

  override suspend fun registerDriver(request: DriverRegisterRequestDto): Response<LoginResponseDto> =
    withContext(Dispatchers.IO) {
      service.registerDriver(request)
    }

  override suspend fun registerManager(request: ManagerRegisterRequestDto): Response<LoginResponseDto> =
    withContext(Dispatchers.IO) {
      service.registerManager(request)
    }

  override suspend fun getProfile(token: String): User =
    withContext(Dispatchers.IO) {
      val response = service.getProfile("Bearer $token")
      if (response.isSuccessful) {
        response.body() ?: throw Exception("Respuesta vacía del servidor")
      } else {
        throw Exception("Error HTTP ${response.code()}: ${response.message()}")
      }
    }
}
