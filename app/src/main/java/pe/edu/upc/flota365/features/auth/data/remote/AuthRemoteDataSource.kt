package pe.edu.upc.flota365.features.auth.data.remote


import pe.edu.upc.flota365.core.network.RetrofitInstance
import pe.edu.upc.flota365.features.auth.data.remote.models.LoginRequestDto
import pe.edu.upc.flota365.features.auth.data.remote.models.LoginResponseDto
import pe.edu.upc.flota365.features.auth.data.remote.services.AuthService
import retrofit2.Response

class AuthRemoteDataSource {

  private val authService = RetrofitInstance.createService(AuthService::class.java)

  suspend fun login(email: String, password: String): Response<LoginResponseDto> {
    val request = LoginRequestDto(email, password)
    return authService.login(request)
  }
}
