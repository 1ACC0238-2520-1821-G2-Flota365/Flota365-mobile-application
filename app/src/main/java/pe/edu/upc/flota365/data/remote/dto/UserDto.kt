package pe.edu.upc.flota365.data.remote.dto

import com.squareup.moshi.Json
import pe.edu.upc.flota365.domain.model.User

/**
 * Data Transfer Object (DTO) que representa la estructura del usuario
 * tal como se recibe o envía al backend.
 */
data class UserDto(
  @Json(name = "id") val id: Int,
  @Json(name = "name") val name: String,
  @Json(name = "email") val email: String,
  @Json(name = "token") val token: String? = null
) {
  /**
   * Convierte este DTO al modelo de dominio.
   * Se usa en los repositorios antes de devolver los datos a la capa de dominio.
   */
  fun toDomain(): User {
    return User(
      id = id,
      name = name,
      email = email,
      token = token
    )
  }
}
