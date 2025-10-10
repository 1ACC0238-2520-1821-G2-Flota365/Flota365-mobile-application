package pe.edu.upc.flota365.data.remote.service

import pe.edu.upc.flota365.data.remote.dto.LoginRequest
import pe.edu.upc.flota365.data.remote.dto.RegisterRequest
import pe.edu.upc.flota365.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Servicio remoto para autenticación de usuarios.
 * Usa Retrofit para definir los endpoints del backend relacionados a login, registro y perfil.
 */
interface AuthService {

  /**
   * Endpoint para iniciar sesión.
   */
  @POST("Auth/login")
  suspend fun login(@Body request: LoginRequest): UserDto

  /**
   * Endpoint para registrar un nuevo usuario.
   */
  @POST("Auth/register")
  suspend fun register(@Body request: RegisterRequest): UserDto

  /**
   * Endpoint para obtener el perfil de un usuario usando su ID y token de autorización.
   */
  @GET("Auth/profile/{userId}")
  suspend fun getProfile(
    @Path("userId") userId: String,
    @Header("Authorization") token: String
  ): UserDto
}
