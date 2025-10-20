package pe.edu.upc.flota365.features.auth.data.remote.services

import pe.edu.upc.flota365.features.auth.data.remote.models.DriverRegisterRequestDto
import pe.edu.upc.flota365.features.auth.data.remote.models.LoginRequestDto
import pe.edu.upc.flota365.features.auth.data.remote.models.LoginResponseDto
import pe.edu.upc.flota365.features.auth.data.remote.models.ManagerRegisterRequestDto
import pe.edu.upc.flota365.features.auth.domain.models.User
import retrofit2.Response
import retrofit2.http.*


interface AuthService {

  @POST("auth/login")
  suspend fun login(@Body request: LoginRequestDto): Response<LoginResponseDto>

  @POST("auth/register/driver")
  suspend fun registerDriver(@Body request: DriverRegisterRequestDto): Response<LoginResponseDto>

  @POST("auth/register/manager")
  suspend fun registerManager(@Body request: ManagerRegisterRequestDto): Response<LoginResponseDto>

  @GET("auth/profile")
  suspend fun getProfile(@Header("Authorization") token: String): Response<User>
}
