package pe.edu.upc.flota365.features.auth.data.remote.services

import pe.edu.upc.flota365.features.auth.data.remote.models.LoginRequestDto
import pe.edu.upc.flota365.features.auth.data.remote.models.LoginResponseDto
import pe.edu.upc.flota365.features.auth.data.remote.models.RegisterRequestDto
import pe.edu.upc.flota365.features.auth.domain.models.User
import retrofit2.Response
import retrofit2.http.*

interface AuthService {

  @Headers("Content-Type: application/json")
  @POST("api/Auth/login")
  suspend fun login(@Body requestDto: LoginRequestDto): Response<LoginResponseDto>

  @Headers("Content-Type: application/json")
  @POST("api/Auth/register")
  suspend fun register(@Body request: RegisterRequestDto): Response<LoginResponseDto>

  // uevo endpoint para obtener el perfil del usuario autenticado
  @GET("api/Auth/me")
  suspend fun getProfile(
    @Header("Authorization") token: String
  ): Response<User>
}
